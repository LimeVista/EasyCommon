@file:JvmName("DensityUtils")

package me.limeice.common.function

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import me.limeice.common.base.BasePresenter


/**
 * Density Utils用于处理像素点与dp单位sp单位之间的相互转换
 *
 * @author Lime
 * Created at 2016.08.16
 * @version 1.0
 */

/**
 * 根据手机的分辨率从 dp 值 转成为 px(像素)值
 *
 * @param dpValue dp值
 * @return px(像素)值
 */
fun Context.dip2px(dpValue: Float): Int {
    val scale = QuickBridge.getDensity(this)
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素)值 转成为 dp 值
 *
 * @param pxValue px(像素)值
 * @return dp值
 */
fun Context.px2dip(pxValue: Float): Int {
    val scale = QuickBridge.getDensity(this)
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param pxValue px(像素)值
 * @return sp值
 */
fun Context.px2sp(pxValue: Float): Int {
    val fontScale = QuickBridge.getDensity(this)
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param spValue sp值
 * @return px(像素)值
 */
fun Context.sp2px(spValue: Float): Int {
    val fontScale = QuickBridge.getDensity(this)
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * 获取屏幕宽度
 *
 * @return (px像素)屏幕宽度
 */
fun Context.getScreenWidth(): Int = resources!!.displayMetrics.widthPixels

/**
 * 获取屏幕高度
 *
 * @return (px像素)屏幕高度
 */
fun Context.getScreenHeight(): Int = resources!!.displayMetrics.heightPixels


/**
 * 获取状态栏高度
 *
 * @return 状态栏高度
 */
fun Context.getStatusBarHeight(): Int {
    var statusBarHeight = 0
    try {
        @SuppressLint("PrivateApi")
        val c = Class.forName("com.android.internal.R\$dimen")
        val obj = c.newInstance()
        val field = c.getField("status_bar_height")
        val x = Integer.parseInt(field.get(obj).toString())
        statusBarHeight = resources.getDimensionPixelSize(x)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return statusBarHeight
}

/**
 * 获取虚拟功能键高度
 */
fun Context.getVirtualBarHeight(): Int {
    val mgr = getSystemService(Context.WINDOW_SERVICE) ?: return 0
    val display = (mgr as WindowManager).defaultDisplay
    val dm = DisplayMetrics()
    if (Build.VERSION.SDK_INT >= 17) {
        display.getRealMetrics(dm)
    } else {
        try {
            val method = Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, dm)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    val pointWH = Point()
    mgr.defaultDisplay.getSize(pointWH)
    return dm.heightPixels - pointWH.y
}

/**
 * 获取屏幕真实高度
 * Android 4.2开始才有的API，在这之前可能无效
 *
 * @return 屏幕高度
 */
fun Context.getScreenRealHeight(): Int {
    val mgr = getSystemService(Context.WINDOW_SERVICE) ?: return 0
    val display = (mgr as WindowManager).defaultDisplay
    val dm = DisplayMetrics()
    return if (Build.VERSION.SDK_INT >= 17) {
        display.getRealMetrics(dm)
        dm.heightPixels
    } else {
        try {
            val method = Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, dm)
            dm.heightPixels
        } catch (e: Exception) {
            display.getMetrics(dm)
            dm.heightPixels
        }
    }
}

/**
 * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
 *
 * @return 平板返回 True，手机返回 False
 */
fun Context.isPad(): Boolean =
        (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE

/**
 * 判断屏幕最小宽度是否大于600dp
 *
 * @return `true` 大于或等于，`false`小于
 */
fun Context.isSW600dp(): Boolean = resources.configuration.smallestScreenWidthDp >= 600


/**
 * 判断屏幕最小宽度是否大于720dp
 *
 * @return `true` 大于或等于，`false`小于
 */
fun Context.isSW720dp(): Boolean = resources.configuration.smallestScreenWidthDp >= 720


/**
 * 根据手机的分辨率从 dp 值 转成为 px(像素)值
 *
 * @param dpValue dp值
 * @return px(像素)值
 */
fun View.dip2px(dpValue: Float): Int {
    val scale = QuickBridge.getDensity(context)
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素)值 转成为 dp 值
 *
 * @param pxValue px(像素)值
 * @return dp值
 */
fun View.px2dip(pxValue: Float): Int {
    val scale = QuickBridge.getDensity(context)
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param pxValue px(像素)值
 * @return sp值
 */
fun View.px2sp(pxValue: Float): Int {
    val fontScale = QuickBridge.getDensity(context)
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param spValue sp值
 * @return px(像素)值
 */
fun View.sp2px(spValue: Float): Int {
    val fontScale = QuickBridge.getDensity(context)
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * 获取屏幕宽度
 *
 * @return (px像素)屏幕宽度
 */
fun View.getScreenWidth(): Int = resources.displayMetrics.widthPixels

/**
 * 获取屏幕高度
 *
 * @return (px像素)屏幕高度
 */
fun View.getScreenHeight(): Int = resources.displayMetrics.heightPixels

/**
 * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
 *
 * @return 平板返回 True，手机返回 False
 */
fun View.isPad(): Boolean =
        (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE

/**
 * 判断屏幕最小宽度是否大于600dp
 *
 * @return `true` 大于或等于，`false`小于
 */
fun View.isSW600dp(): Boolean = resources.configuration.smallestScreenWidthDp >= 600


/**
 * 判断屏幕最小宽度是否大于720dp
 *
 * @return `true` 大于或等于，`false`小于
 */
fun View.isSW720dp(): Boolean = resources.configuration.smallestScreenWidthDp >= 720

/**
 * 根据手机的分辨率从 dp 值 转成为 px(像素)值
 *
 * @param dpValue dp值
 * @return px(像素)值
 */
fun BasePresenter<*, *>.dip2px(dpValue: Float): Int {
    val scale = QuickBridge.getDensity(mContext)
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素)值 转成为 dp 值
 *
 * @param pxValue px(像素)值
 * @return dp值
 */
fun BasePresenter<*, *>.px2dip(pxValue: Float): Int {
    val scale = QuickBridge.getDensity(mContext)
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param pxValue px(像素)值
 * @return sp值
 */
fun BasePresenter<*, *>.px2sp(pxValue: Float): Int {
    val fontScale = QuickBridge.getDensity(mContext)
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param spValue sp值
 * @return px(像素)值
 */
fun BasePresenter<*, *>.sp2px(spValue: Float): Int {
    val fontScale = QuickBridge.getDensity(mContext)
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * 获取屏幕宽度
 *
 * @return (px像素)屏幕宽度
 */
fun BasePresenter<*, *>.getScreenWidth(): Int {
    return mContext.resources.displayMetrics.widthPixels
}

/**
 * 获取屏幕高度
 *
 * @return (px像素)屏幕高度
 */
fun BasePresenter<*, *>.getScreenHeight(): Int {
    return mContext.resources.displayMetrics.heightPixels
}

/**
 * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
 *
 * @return 平板返回 True，手机返回 False
 */
fun BasePresenter<*, *>.isPad(): Boolean =
        (mContext.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE

/**
 * 判断屏幕最小宽度是否大于600dp
 *
 * @return `true` 大于或等于，`false`小于
 */
fun BasePresenter<*, *>.isSW600dp(): Boolean = mContext.resources.configuration.smallestScreenWidthDp >= 600


/**
 * 判断屏幕最小宽度是否大于720dp
 *
 * @return `true` 大于或等于，`false`小于
 */
fun BasePresenter<*, *>.isSW720dp(): Boolean = mContext.resources.configuration.smallestScreenWidthDp >= 720