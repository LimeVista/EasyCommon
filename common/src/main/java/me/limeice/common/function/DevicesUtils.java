package me.limeice.common.function;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.util.ArrayMap;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import me.limeice.common.base.EasyCommon;

/**
 * 设备信息相关处理工具
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2018/03/25
 *     desc  : 设备信息相关处理工具
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
@SuppressWarnings("WeakerAccess")
public final class DevicesUtils {

    /**
     * 获得CPU核心数
     *
     * @return CPU核心数
     */
    public static int getNumCores() {
        File dir = new File("/sys/devices/system/cpu");
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return Pattern.matches(
                        "cpu[0-9]", pathname.getName());
            }
        });
        return files.length;
    }

    /**
     * 获取处理器信息
     *
     * @return 处理器信息
     * @throws IOException 获取/读取失败
     */
    @NonNull
    public static String getProcessorName() throws IOException {
        FileReader reader = new FileReader("/proc/cpuinfo");
        BufferedReader br = new BufferedReader(reader);
        String text = br.readLine();
        String[] info = text.split(":\\s+", 2);
        CloseUtils.closeIOQuietly(reader, br);
        return info[1];
    }

    /**
     * 获取CPU详细信息
     *
     * @return CPU详细信息文本
     * @throws IOException 获取/读取失败
     */
    @NonNull
    public static String getCpuInfo() throws IOException {
        FileReader reader = new FileReader("/proc/cpuinfo");
        BufferedReader br = new BufferedReader(reader);
        String line;
        StringBuilder sb = new StringBuilder(1024);
        while ((line = br.readLine()) != null)
            sb.append(line).append('\n');
        return sb.toString();
    }

    /**
     * 获取WIFI的MAC地址
     *
     * @return MAC地址
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(android.Manifest.permission.ACCESS_WIFI_STATE)
    public static String getWifiMac() {
        WifiManager manager = (WifiManager) EasyCommon.getApp().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (manager == null) throw new NullPointerException("Get WIFI SERVICE Failed");
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获取设备内存相关信息
     *
     * @return 返回详细内存信息描述
     * @throws IOException IOE
     */
    @NonNull
    public static Map<String, String> getMemInfo() throws IOException {
        FileReader reader = new FileReader("/proc/meminfo");
        BufferedReader br = new BufferedReader(reader);
        ArrayMap<String, String> map = new ArrayMap<>();
        String line;
        String[] kv;
        while ((line = br.readLine()) != null) {
            kv = line.split(":\\s+", 2);
            if (kv[0] == null || kv[0].trim().equals(""))
                continue;
            map.put(kv[0].trim(), kv[1].trim());
        }
        br.close();
        reader.close();
        return map;
    }

    /**
     * 获取设备IMEI和设备序列号，数组索引（index）第0个为IMEI，第1个为SimSerialNumber
     *
     * @return IMEI和SimSerialNumber(值可能为空)
     */
    @NonNull
    @SuppressLint("HardwareIds")
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    public static String[] getImeiWithSN() {
        String[] info = new String[2];
        TelephonyManager tm = (TelephonyManager) EasyCommon.getApp()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            if (Build.VERSION.SDK_INT < 26) {
                info[0] = tm.getDeviceId(); //IMEI
            } else {
                info[0] = tm.getImei(); //IMEI
            }
            info[1] = tm.getSimSerialNumber();  //SimSerialNumber
        }
        return info;
    }

    /**
     * 获取设备型号
     *
     * @return 设备型号
     */
    @NonNull
    public static String getDevicesModel() {
        return Build.MODEL;
    }

    /**
     * 获取设备制造商
     *
     * @return 设备制造商
     */
    @NonNull
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取全局UUID
     *
     * @return UUID
     */
    @Nullable
    public static String getUUID() {
        String uuid = null;
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(EasyCommon.getApp());
        if (preferences != null)
            uuid = preferences.getString("uuid", "");
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            if (preferences != null)
                preferences.edit().putString("uuid", uuid).apply();
        }
        return uuid;
    }

    /**
     * 初始化并获取屏幕相关信息
     *
     * @return DisplayMetrics
     */
    @NonNull
    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager mgr = (WindowManager) EasyCommon.getApp()
                .getSystemService(Context.WINDOW_SERVICE);
        mgr.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
}