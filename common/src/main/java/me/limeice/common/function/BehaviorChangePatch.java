package me.limeice.common.function;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Behavior change patch Android 行为变更补丁
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2019/08/16
 *     desc  : Android 行为变更补丁
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
public final class BehaviorChangePatch {

    private static final String TAG = "BehaviorChangePatch";
    private static final String ARC_DEVICE_PATTERN = ".+_cheets|cheets_.+";

    /**
     * Caused by: java.lang.RuntimeException:
     * Using WebView from more than one process at once with the same data directory is not supported.
     *
     * @param context            {@link Context}
     * @param defaultProcessName default process name, Normally the same package name.
     * @see <a href="https://crbug.com/558377">https://crbug.com/558377</>
     */
    public static void fixWebViewSameDirFromP(@NonNull Context context, String defaultProcessName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            final String processName = processName(context);
            if (processName == null) return;
            if (!defaultProcessName.equals(processName)) {
                WebView.setDataDirectorySuffix(processName);
                Log.d(TAG, "Use patch for web view. Process name " + processName);
            }
        }
    }

    /**
     * Check the platform is Arc. eg: Chrome OS
     *
     * @param context {@link Context}
     * @return {@code true} or {@code false}
     */
    public static boolean isARChon(@NonNull Context context) {
        final String device = Build.DEVICE;
        return (device != null && device.matches(ARC_DEVICE_PATTERN)) ||
                context.getPackageManager().hasSystemFeature("org.chromium.arc.device_management");
    }

    /**
     * Get current process name
     *
     * @param context {@link Context}
     * @return if successful return  process name, otherwise return null.
     */
    @Nullable
    private static String processName(@NonNull Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return null;
        for (ActivityManager.RunningAppProcessInfo processInfo : am.getRunningAppProcesses()) {
            if (processInfo.pid == Process.myPid()) {
                return processInfo.processName;
            }
        }
        return null;
    }
}
