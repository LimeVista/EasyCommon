package me.limeice.common.function;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.util.List;

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
     * @param context 上下文容器
     * @return 版本信息（若获取失败返回null）
     */
    public static String getVerName(@NonNull Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取版本号
     *
     * @param context 上下文容器
     * @return 版本号（若获取失败返回0）
     */
    public static int getVerCode(@NonNull Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 检测服务是否运行
     *
     * @param context   上下文容器
     * @param className 服务类名
     * @return 运行状态
     */
    public static boolean isServiceRunning(@NonNull Context context, String className) {
        ActivityManager manager = (ActivityManager) context.
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
     * @param context   上下文容器
     * @param className 服务类名
     * @return 是否执行成功
     * @throws ClassNotFoundException 无找到此类
     */
    public static boolean stopRunningService(@NonNull Context context, String className)
            throws ClassNotFoundException {
        Intent it = new Intent(context, Class.forName(className));
        return context.stopService(it);
    }
}
