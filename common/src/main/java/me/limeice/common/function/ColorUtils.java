package me.limeice.common.function;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * ColorUtils 用于处理颜色资源的和转换
 *
 * @author Lime
 * Created at 2016.08.16
 * @version 2.1
 */
public final class ColorUtils {

    private ColorUtils() {
        throw new UnsupportedOperationException("Don't instantiate...");
    }

    /**
     * 根据Attribute属性的资源ID值，获取颜色值
     *
     * @param context Context容器
     * @param attr    属性ID
     * @return 颜色值
     * @see AttrUtils#getAttrColor(Context, int)
     */
    public static int getAttrColor(@NonNull Context context, @AttrRes int attr) {
        return AttrUtils.getAttrColor(context, attr);
    }

    /**
     * 根据Resources的Color id值生成ColorDrawable
     *
     * @param context Context容器
     * @param id      res中颜色的id值
     * @return ColorDrawable画布
     */
    @NonNull
    public static ColorDrawable getColorDrawable(@NonNull Context context, @ColorRes int id) {
        return new ColorDrawable(ContextCompat.getColor(context, id));
    }

    /**
     * 从ColorInt提取颜色值，色值0~255
     *
     * @param color 颜色值
     * @return 数组长度为4, <code> array[0] = A; array[1] = R; array[2] = G; array[3] = B </code>
     */
    @NonNull
    public static byte[] getARGB(@ColorInt int color) {
        byte[] colorByte = new byte[4];
        colorByte[0] = (byte) (color >>> 24);
        colorByte[1] = (byte) ((color & 0xFF0000) >> 16);
        colorByte[2] = (byte) ((color & 0xFF00) >> 8);
        colorByte[3] = (byte) (color & 0xFF);
        return colorByte;
    }

    /**
     * 从ColorInt提取颜色值，色值0~255
     *
     * @param color 颜色值
     * @return 数组长度为3, array[0] = R; array[1] = G; array[2] = B
     */
    @NonNull
    public static byte[] getRGB(@ColorInt int color) {
        byte[] colorByte = new byte[3];
        colorByte[0] = (byte) ((color & 0xFF0000) >> 16);
        colorByte[1] = (byte) ((color & 0xFF00) >> 8);
        colorByte[2] = (byte) (color & 0xFF);
        return colorByte;
    }
}