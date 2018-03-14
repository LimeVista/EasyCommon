package me.limeice.common.function;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * DensityUtil用于处理像素点与dp单位sp单位之间的相互转换
 *
 * @author Lime
 *         Created at 2016.08.16
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class DensityUtils {

    /**
     * 根据手机的分辨率从 dp 值 转成为 px(像素)值
     *
     * @param context 被转换值的所在Context容器
     * @param dpValue dp值
     * @return px(像素)值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素)值 转成为 dp 值
     *
     * @param context 被转换值的所在Context容器
     * @param pxValue px(像素)值
     * @return dp值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
     *
     * @param pxValue px(像素)值
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
     *
     * @param spValue sp值
     * @return px(像素)值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context 被转换值的所在Context容器
     * @return (px像素)屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context 被转换值的所在Context容器
     * @return (px像素)屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取状态栏高度
     *
     * @param context 被转换值的所在Context容器
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            @SuppressLint("PrivateApi") Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取虚拟功能键高度
     */
    public static int getVirtualBarHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null)
            return 0;
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(dm);
        } else {
            try {
                Method method = Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Point pointWH = new Point();
        windowManager.getDefaultDisplay().getSize(pointWH);
        return dm.heightPixels - pointWH.y;
    }

    /**
     * 获取屏幕真实高度
     * Android 4.2开始才有的API，在这之前可能无效
     *
     * @param context 上下文容器
     * @return 屏幕高度
     */
    public static int getScreenRealHeight(Context context) {
        int h = 0;
        WindowManager mgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (mgr == null)
            return h;
        Display display = mgr.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(dm);
            h = dm.heightPixels;
        } else {
            try {
                Method method = Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
                h = dm.heightPixels;
            } catch (Exception e) {
                display.getMetrics(dm);
                h = dm.heightPixels;
            }
        }
        return h;
    }
}