package me.limeice.common.function;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.TypedValue;

/**
 * AttrUtils 用于处理属性资源
 *
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2018/11/30
 *     desc  : 用于处理属性资源
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
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
