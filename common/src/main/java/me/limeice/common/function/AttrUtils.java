package me.limeice.common.function;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.TypedValue;

/**
 * ColorUtils 用于处理属性资源
 *
 * @author Lime
 * Created at 2018.08.31
 * @version 1.0
 */
public final class AttrUtils {

    /**
     * 根据Attribute属性的资源ID值，获取颜色值
     *
     * @param context Context容器
     * @param attr    属性ID
     * @return 颜色值
     */
    @ColorInt
    public static int getAttrColor(@NonNull Context context, @AttrRes int attr) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }

    /**
     * 根据Attribute属性的资源ID值，获取尺寸值
     *
     * @param context Context容器
     * @param attr    属性ID
     * @return 尺寸，单位：px
     */
    public static int getAttrDimenPixelSize(@NonNull Context context, @AttrRes int attr) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(
                typedValue.data,
                context.getResources().getDisplayMetrics()
        );
    }
}
