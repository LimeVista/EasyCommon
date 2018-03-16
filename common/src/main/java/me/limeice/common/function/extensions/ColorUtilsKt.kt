@file: JvmName("ColorUtilsKt")
@file:Suppress("NOTHING_TO_INLINE")

package me.limeice.common.function.extensions

import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.ColorRes
import me.limeice.common.function.ColorUtils

/**
 * <pre>
 *      author: LimeVista(Lime)
 *      time  : 2018/03/15
 *      desc  : 颜色相关工具类
 *      github: https://github.com/LimeVista/EasyCommon
 *</pre>
 */

/* Red */
inline fun Int.r() = (this and 0xFF0000) shr 16

/* Green */
inline fun Int.g() = (this and 0xFF00) shr 8

/* Blue */
inline fun Int.b() = this and 0xFF

/* Alpha */
inline fun Int.a() = this ushr 24

/* {@link ColorUtils#getAttrColor(Context, Int)}*/
inline fun Context.getAttrColor(@AttrRes attr: Int) = ColorUtils.getAttrColor(this, attr)

/* {@link ColorUtils#getColorDrawable(Context, Int)} */
inline fun Context.getColorDrawable(@ColorRes id: Int) = ColorUtils.getColorDrawable(this, id)

/**
 * 从ColorInt提取颜色值，色值0~255
 *
 * @return 数组长度为4, <code> array[0] = A; array[1] = R; array[2] = G; array[3] = B </code>
 */
inline fun Int.getARGB() = ColorUtils.getARGB(this)

/**
 * 从ColorInt提取颜色值，色值0~255
 *
 * @return 数组长度为3, array[0] = R; array[1] = G; array[2] = B </code>
 */
inline fun Int.getRGB() = ColorUtils.getRGB(this)