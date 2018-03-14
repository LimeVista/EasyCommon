@file: JvmName("DrawableUtilsKt")
@file:Suppress("NOTHING_TO_INLINE")

package me.limeice.common.function.extensions

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import me.limeice.common.function.DrawableUtils


/**
 * <pre>
 *      author: LimeVista(Lime)
 *      time  : 2018/03/14
 *      desc  : Drawable处理工具扩展类
 *      github: https://github.com/LimeVista/EasyCommon
 *</pre>
 */

/**
 * 画布高亮着色
 *
 * @param colorStateList 颜色
 * @return 着色后的画布
 */
inline fun Drawable.tintDrawable(colorStateList: ColorStateList) =
        DrawableUtils.tintDrawable(this, colorStateList)

/**
 * 画布高亮着色
 *
 * @param color 单色
 * @return 着色后的画布
 */
inline fun Drawable.tintDrawable(@ColorInt color: Int) =
        DrawableUtils.tintDrawable(this, color)

