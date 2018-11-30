package me.limeice.common.function;


import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

/**
 * 权限处理工具
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2018/11/30
 *     desc  : 权限处理工具
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
public final class PermissionUtils {

    /**
     * 检查是否拥有请求权限
     *
     * @param context     Context
     * @param permissions 权限列表,{@link android.Manifest.permission}
     * @return {@code true} 拥有权限，否则不具备完整权限
     */
    public static boolean isGranted(@NonNull Context context, @NonNull final String... permissions) {
        Objects.checkNonNull(permissions);
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && PackageManager.PERMISSION_GRANTED !=
                    ContextCompat.checkSelfPermission(context, permission))
                return false;
        }
        return true;
    }
}

