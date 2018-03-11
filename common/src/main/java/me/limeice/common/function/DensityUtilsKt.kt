@file: JvmName("DensityUtilsKt")
@file:Suppress("unused")

package me.limeice.common.function

/**
 * DensityUtil用于处理像素点与dp单位sp单位之间的相互转换
 *
 * @author Lime
 * Created at 2018.03.01
 * @version 1.1
 */

import android.app.Activity
import android.content.Context
import android.view.View
import me.limeice.common.base.BasePresenter

/**
 * 根据手机的分辨率从 dp 值 转成为 px(像素)值
 *
 * @param dpValue dp值
 * @return px(像素)值
 */
fun Activity.dip2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素)值 转成为 dp 值
 *
 * @param pxValue px(像素)值
 * @return dp值
 */
fun Activity.px2dip(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param pxValue px(像素)值
 * @return sp值
 */
fun Activity.px2sp(pxValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param spValue sp值
 * @return px(像素)值
 */
fun Activity.sp2px(spValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * 获取屏幕宽度
 *
 * @return (px像素)屏幕宽度
 */
fun Activity.getScreenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

/**
 * 获取屏幕高度
 *
 * @return (px像素)屏幕高度
 */
fun Activity.getScreenHeight(): Int {
    return resources.displayMetrics.heightPixels
}

/**
 * 根据手机的分辨率从 dp 值 转成为 px(像素)值
 *
 * @param dpValue dp值
 * @return px(像素)值
 */
fun Context.dip2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素)值 转成为 dp 值
 *
 * @param pxValue px(像素)值
 * @return dp值
 */
fun Context.px2dip(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param pxValue px(像素)值
 * @return sp值
 */
fun Context.px2sp(pxValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param spValue sp值
 * @return px(像素)值
 */
fun Context.sp2px(spValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * 获取屏幕宽度
 *
 * @return (px像素)屏幕宽度
 */
fun Context.getScreenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

/**
 * 获取屏幕高度
 *
 * @return (px像素)屏幕高度
 */
fun Context.getScreenHeight(): Int {
    return resources.displayMetrics.heightPixels
}

/**
 * 根据手机的分辨率从 dp 值 转成为 px(像素)值
 *
 * @param dpValue dp值
 * @return px(像素)值
 */
fun View.dip2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素)值 转成为 dp 值
 *
 * @param pxValue px(像素)值
 * @return dp值
 */
fun View.px2dip(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param pxValue px(像素)值
 * @return sp值
 */
fun View.px2sp(pxValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param spValue sp值
 * @return px(像素)值
 */
fun View.sp2px(spValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * 获取屏幕宽度
 *
 * @return (px像素)屏幕宽度
 */
fun View.getScreenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

/**
 * 获取屏幕高度
 *
 * @return (px像素)屏幕高度
 */
fun View.getScreenHeight(): Int {
    return resources.displayMetrics.heightPixels
}

/**
 * 根据手机的分辨率从 dp 值 转成为 px(像素)值
 *
 * @param dpValue dp值
 * @return px(像素)值
 */
fun BasePresenter<*, *>.dip2px(dpValue: Float): Int {
    val scale = mContext.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素)值 转成为 dp 值
 *
 * @param pxValue px(像素)值
 * @return dp值
 */
fun BasePresenter<*, *>.px2dip(pxValue: Float): Int {
    val scale = mContext.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param pxValue px(像素)值
 * @return sp值
 */
fun BasePresenter<*, *>.px2sp(pxValue: Float): Int {
    val fontScale = mContext.resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
 *
 * @param spValue sp值
 * @return px(像素)值
 */
fun BasePresenter<*, *>.sp2px(spValue: Float): Int {
    val fontScale = mContext.resources.displayMetrics.scaledDensity
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

