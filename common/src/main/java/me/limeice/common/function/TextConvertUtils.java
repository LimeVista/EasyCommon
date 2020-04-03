package me.limeice.common.function;

import android.os.Build;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 用于处理文本
 *
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2020/04/03
 *     desc  : 用于处理文本
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
public final class TextConvertUtils {

    private TextConvertUtils() {
        throw new AssertionError("No TextConvertUtils instances for you!");
    }

    /**
     * Return the string of decode html-encode string.
     *
     * @param input The input.
     * @return the string of decode html-encode string
     */
    @NonNull
    @SuppressWarnings("deprecation")
    public static CharSequence string2Html(@NonNull final String input) {
        Objects.requireNonNull(input);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(input);
        }
    }

    /**
     * Return the url-encoded string.
     *
     * @param input The input.
     * @return The url-encoded string
     */
    @NonNull
    public static String urlEncode(@Nullable final String input) {
        return urlEncode(input, "UTF-8");
    }

    /**
     * Return the url-encoded string.
     *
     * @param input       The input.
     * @param charsetName The name of charset.
     * @return The url-encoded string
     */
    @NonNull
    public static String urlEncode(@Nullable final String input, @NonNull final String charsetName) {
        if (input == null || input.length() == 0) return "";
        try {
            return URLEncoder.encode(input, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Return the string of decode url-encoded string.
     *
     * @param input The input.
     * @return The string of decode url-encoded string
     */
    @NonNull
    public static String urlDecode(@Nullable final String input) {
        return urlDecode(input, "UTF-8");
    }

    /**
     * Return the string of decode url-encoded string.
     *
     * @param input       The input.
     * @param charsetName The name of charset.
     * @return The string of decode url-encoded string
     */
    @NonNull
    public static String urlDecode(@Nullable final String input, @NonNull final String charsetName) {
        if (input == null || input.length() == 0) return "";
        try {
            return URLDecoder.decode(input, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
}
