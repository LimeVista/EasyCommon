@file:JvmName("DrawableUtils")

package me.limeice.common.function

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat

/**
 * 画布处理工具
 * <pre>
 * author: LimeVista(Lime)
 * time  : 2018/03/30
 * desc  : 关闭相关工具类
 * github: https://github.com/LimeVista/EasyCommon
</pre> *
 */

/**
 * 画布着色
 *
 * @param colors   颜色
 * @return 着色后的画布
 */
fun Drawable.tintDrawable(colors: ColorStateList): Drawable {
    val wrappedDrawable = DrawableCompat.wrap(this)
    DrawableCompat.setTintList(wrappedDrawable, colors)
    return wrappedDrawable
}

/**
 * 画布着色
 *
 * @param color    单色
 * @return 着色后的画布
 */
fun Drawable.tintDrawable(@ColorInt color: Int): Drawable {
    val wrappedDrawable = DrawableCompat.wrap(this)
    DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color))
    return wrappedDrawable
}


/**
 * 画布着色
 *
 * @param id    画布资源
 * @param color 单色
 * @return 着色后的画布
 */

fun tintDrawable(context: Context, @DrawableRes id: Int, @ColorInt color: Int): Drawable? {
    val d = ContextCompat.getDrawable(context, id) ?: return null
    val wrappedDrawable = DrawableCompat.wrap(d)
    DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color))
    return wrappedDrawable
}

