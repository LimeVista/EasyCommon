package me.limeice.common.function;

import androidx.annotation.NonNull;

/**
 * 用于处理文本
 *
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2018/11/30
 *     desc  : 用于处理文本
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 *
 * @deprecated use {@link TextConvertUtils}
 */
@Deprecated
public final class TextEncodeUtils {

    private TextEncodeUtils() {
        throw new AssertionError("No TextEncodeUtils instances for you!");
    }

    /**
     * Return the string of decode html-encode string.
     *
     * @param input The input.
     * @return the string of decode html-encode string
     */
    @NonNull
    public static CharSequence string2Html(@NonNull final String input) {
        return TextConvertUtils.string2Html(input);
    }
}
