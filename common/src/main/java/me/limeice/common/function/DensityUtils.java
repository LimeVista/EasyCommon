package me.limeice.common.function;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Method;

import me.limeice.common.base.EasyCommon;

/**
 * Density Utils用于处理像素点与dp单位sp单位之间的相互转换
 *
 * @author LimeVista(Lime)
 * Created at 2016.08.16, Last Update at 2019.03.23
 * github: https://github.com/LimeVista/EasyCommon
 * @version 1.0
 */
public final class DensityUtils {

    private DensityUtils() {
        throw new UnsupportedOperationException("Don't instantiate...");
    }

    /**
     * 根据手机的分辨率从 dp 值 转成为 px(像素)值
     *
     * @param dpValue dp值
     * @return px(像素)值
     */
    public static int dip2px(float dpValue) {
        float scale = EasyCommon.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素)值 转成为 dp 值
     *
     * @param pxValue px(像素)值
     * @return dp值
     */
    public static int px2dip(float pxValue) {
        float scale = EasyCommon.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机分辨率，将 sp 值转换为 px 值，
     * 保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
     *
     * @param spValue sp值
     * @return px(像素)值
     */
    public static int sp2px(float spValue) {
        float fontScale = EasyCommon.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机分辨率，将 px 值转换为 sp 值，
     * 保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
     *
     * @param pxValue px(像素)值
     * @return sp值
     */
    public static int px2sp(float pxValue) {
        float fontScale = EasyCommon.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @return (px像素)屏幕宽度
     */
    public static int getScreenWidth() {
        Application app = EasyCommon.getApp();
        WindowManager wm = (WindowManager) app.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return app.getResources().getDisplayMetrics().widthPixels;
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return (px像素)屏幕高度
     */
    public static int getScreenHeight() {
        Application app = EasyCommon.getApp();
        WindowManager wm = (WindowManager) app.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return app.getResources().getDisplayMetrics().heightPixels;
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取应用宽度
     *
     * @param act Activity
     * @return (px像素)宽度
     */
    public static int getAppScreenWidth(@NonNull Activity act) {
        return act.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取应用高度
     *
     * @param act Activity
     * @return (px像素)高度
     */
    public static int getAppScreenHeight(@NonNull Activity act) {
        return act.getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * 获取应用宽度
     *
     * @param win Window
     * @return (px像素)宽度
     */
    public static int getAppScreenWidth(@NonNull Window win) {
        Point size = new Point();
        win.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    /**
     * 获取应用高度
     *
     * @param win Window
     * @return (px像素)高度
     */
    public static int getAppScreenHeight(@NonNull Window win) {
        Point size = new Point();
        win.getWindowManager().getDefaultDisplay().getSize(size);
        return size.y;
    }

    /**
     * 获取应用宽度
     *
     * @param v View
     * @return (px像素)宽度
     */
    public static int getAppScreenWidth(@NonNull View v) {
        return v.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取应用高度
     *
     * @param v View
     * @return (px像素)高度
     */
    public static int getAppScreenHeight(@NonNull View v) {
        return v.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取应用宽度
     *
     * @param v Fragment
     * @return (px像素)宽度
     */
    public static int getAppScreenWidth(@NonNull Fragment v) {
        return v.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取应用高度
     *
     * @param v Fragment
     * @return (px像素)高度
     */
    public static int getAppScreenHeight(@NonNull Fragment v) {
        return v.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度，如果失败则返回 0
     */
    @SuppressLint("PrivateApi")
    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId == 0) return 0;
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取屏幕真实高度
     * Android 4.2开始才有的API，在这之前可能无效
     *
     * @return 屏幕高度
     */
    public static int getScreenRealHeight() {
        WindowManager mgr = (WindowManager) EasyCommon.getApp()
                .getSystemService(Context.WINDOW_SERVICE);
        if (mgr == null) return 0;
        final Display display = mgr.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(dm);
            return dm.heightPixels;
        } else {
            try {
                Method method = Class.forName("android.view.Display")
                        .getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
                return dm.heightPixels;
            } catch (Exception ex) {
                display.getMetrics(dm);
                return dm.heightPixels;
            }
        }
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @return {@code true} or {@code false}
     */
    public static boolean isPad() {
        return (EasyCommon.getApp().getResources()
                .getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                > Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断屏幕最小宽度是否大于 600dp
     *
     * @return `true` 大于或等于，`false`小于
     */
    public static boolean isSW600dp() {
        return EasyCommon.getApp().getResources()
                .getConfiguration()
                .smallestScreenWidthDp >= 600;
    }

    /**
     * 判断屏幕最小宽度是否大于 720dp
     *
     * @return `true` 大于或等于，`false`小于
     */
    public static boolean isSW720dp() {
        return EasyCommon.getApp().getResources()
                .getConfiguration()
                .smallestScreenWidthDp >= 720;
    }

    /**
     * 获取虚拟功能键高度，此发放已经弃用。
     * 如果你需要仅仅获取导航栏高度，请使用{@link #getNavBarHeight()}，
     * 如果你需要判定是否存在导航栏，然后获取高度请使用{@link #getAutoNavBarHeight(Window)}
     *
     * @return 高度
     * @see #getNavBarHeight()
     * @see #getAutoNavBarHeight(Window)
     */
    @Deprecated
    public static int getVirtualBarHeight() {
        final WindowManager mgr = (WindowManager) EasyCommon.getApp()
                .getSystemService(Context.WINDOW_SERVICE);
        if (mgr == null)
            return 0;
        final Display display = mgr.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(dm);
        } else {
            try {
                Method method = Class.forName("android.view.Display")
                        .getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Point pointWH = new Point();
        display.getSize(pointWH);
        return dm.heightPixels - pointWH.y;
    }

    /**
     * 自动判定获取虚拟栏（导航栏）高度
     *
     * @param window Window
     * @return 高度
     */
    public static int getAutoNavBarHeight(@NonNull final Window window) {
        if (!isNavBarVisible(window))
            return 0;
        return getNavBarHeight();
    }

    /**
     * 获取虚拟栏（导航栏）高度
     *
     * @return 高度
     */
    public static int getNavBarHeight() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId == 0)
            return 0;
        return res.getDimensionPixelSize(resourceId);
    }

    /**
     * 是否显示虚拟拦（导航栏）
     *
     * @param window {@link Window}
     * @return 是否显示虚拟拦
     */
    public static boolean isNavBarVisible(@NonNull final Window window) {
        Resources res = Resources.getSystem();
        int id = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id == 0) return false;
        boolean isVisible = res.getBoolean(id);
        if (isVisible) {
            isVisible = (window.getDecorView().getSystemUiVisibility() &
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
        }
        return isVisible;
    }
}