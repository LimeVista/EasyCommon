package me.limeice.common.function;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;

import java.util.List;

import me.limeice.common.base.EasyCommon;

/**
 * App处理工具
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2018/03/24
 *     desc  : App工具类
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
public final class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("Don't instantiate...");
    }

    /**
     * 获取版本信息
     *
     * @return 版本信息（若获取失败返回null）
     */
    public static String getVerName() {
        final Application app = EasyCommon.getApp();
        try {
            return app.getPackageManager()
                    .getPackageInfo(app.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取版本号
     *
     * @return 版本号（若获取失败返回0）
     */
    public static int getVerCode() {
        final Application app = EasyCommon.getApp();
        try {
            return app.getPackageManager()
                    .getPackageInfo(app.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取版本号
     *
     * @return 版本号（若获取失败返回0）
     */
    public static long getLongVerCode() {
        final Application app = EasyCommon.getApp();
        try {
            return app.getPackageManager()
                    .getPackageInfo(app.getPackageName(), 0).getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 检测服务是否运行
     *
     * @param className 服务类名
     * @return 运行状态
     */
    public static boolean isServiceRunning(@NonNull String className) {
        Objects.checkNonNull(className);
        ActivityManager manager = (ActivityManager) EasyCommon.getApp().
                getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) return false;
        List<ActivityManager.RunningServiceInfo> lists = manager
                .getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo i : lists)
            if (className.equals(i.service.getClassName()))
                return true;
        return false;
    }

    /**
     * 停止运行中的服务
     *
     * @param className 服务类名
     * @return 是否执行成功
     * @throws ClassNotFoundException 无找到此类
     */
    public static boolean stopRunningService(@NonNull String className)
            throws ClassNotFoundException {
        Objects.checkNonNull(className);
        final Application app = EasyCommon.getApp();
        Intent it = new Intent(app, Class.forName(className));
        return app.stopService(it);
    }
}
