package me.limeice.common.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;


import me.limeice.common.function.Objects;

/**
 * EasyCommon Lifecycle
 *
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2019.03.18
 *     desc  : EasyCommon Lifecycle
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
public final class EasyCommon {

    private static final EasyCommon COMMON_LIFECYCLE = new EasyCommon();

    private Application mApp = null;

    /**
     * Init EasyCommon.
     * If the reflection works properly, it is not necessary to call it.
     *
     * @param context {@link Context}
     */
    public static void init(@NonNull final Context context) {
        Objects.checkNonNull(context);
        synchronized (COMMON_LIFECYCLE) {
            COMMON_LIFECYCLE.mApp = (Application) context.getApplicationContext();
        }
    }

    /**
     * Get application, if not call {@link EasyCommon#init(Context)},
     * try call {@link EasyCommon#getAppByReflect()}
     *
     * @return {@link Application}, it is non-null.
     */
    @NonNull
    public static Application getApp() {
        if (COMMON_LIFECYCLE.mApp == null) {
            synchronized (COMMON_LIFECYCLE) {
                if (COMMON_LIFECYCLE.mApp == null)
                    COMMON_LIFECYCLE.mApp = getAppByReflect();
            }
        }
        return COMMON_LIFECYCLE.mApp;
    }

    /**
     * Get application by reflect
     *
     * @return {@link Application}, it is non-null.
     * @see android.app.ActivityThread#getApplication()
     */
    @SuppressLint("PrivateApi")
    @SuppressWarnings("JavadocReference")
    private static Application getAppByReflect() {
        Object app = null;
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            app = activityThread.getMethod("getApplication").invoke(thread);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Application) Objects.requireNonNull(app, "EasyCommon.init is not called.");
    }
}
