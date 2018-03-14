package me.limeice.common.function;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * 画布处理工具
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2018/03/12
 *     desc  : 关闭相关工具类
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
public final class DrawableUtils {

    private DrawableUtils() {
        throw new UnsupportedOperationException("Don't instantiate...");
    }

    /**
     * 画布着色
     *
     * @param drawable 画布
     * @param colors   颜色
     * @return 着色后的画布
     */
    @NonNull
    public static Drawable tintDrawable(@NonNull Drawable drawable, @NonNull ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /**
     * 画布着色
     *
     * @param drawable 画布
     * @param color    单色
     * @return 着色后的画布
     */
    @NonNull
    public static Drawable tintDrawable(@NonNull Drawable drawable, @ColorInt int color) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color));
        return wrappedDrawable;
    }

    /**
     * 画布着色
     *
     * @param id    画布资源
     * @param color 单色
     * @return 着色后的画布
     */
    @Nullable
    public static Drawable tintDrawable(@NonNull Context context, @DrawableRes int id, @ColorInt int color) {
        Drawable d = ContextCompat.getDrawable(context, id);
        if (d == null) return null;
        final Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color));
        return wrappedDrawable;
    }
}
