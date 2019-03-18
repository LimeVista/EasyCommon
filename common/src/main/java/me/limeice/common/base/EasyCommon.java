package me.limeice.common.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;


import me.limeice.common.function.Objects;

/**
 * EasyCommon Lifecycle
 *
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2019.01.03
 *     desc  : Android Main Thread Scheduler
 *     github: https://github.com/LimeVista/EasyCommonLifecycle
 * </pre>
 */
public final class EasyCommonLifecycle {

    private static final EasyCommonLifecycle COMMON_LIFECYCLE = new EasyCommonLifecycle();

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
     * Get application, if not call {@link EasyCommonLifecycle#init(Context)},
     * try call {@link EasyCommonLifecycle#getAppByReflect()}
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
        return (Application) Objects.requireNonNull(app, "EasyCommonLifecycle.init is not called.");
    }
}
