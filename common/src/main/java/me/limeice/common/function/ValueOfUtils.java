package me.limeice.common.function;

/**
 * 值转换工具
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2018/04/16
 *     desc  : 值转换工具 工具类
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
public final class ValueOfUtils {

    private ValueOfUtils() {
        throw new AssertionError("No ValueOfUtils instances for you!");
    }

    /**
     * convert a string value to a int value (String -> Int)
     *
     * @param value      String Number
     * @param defaultVal Default value
     * @return Number
     */
    public static int valueOf(String value, int defaultVal) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException ex) {
            return defaultVal;
        }
    }

    /**
     * convert a string value to a long value (String -> Long)
     *
     * @param value      String Number
     * @param defaultVal Default value
     * @return Number
     */
    public static long valueOf(String value, long defaultVal) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException ex) {
            return defaultVal;
        }
    }

    /**
     * convert a string value to a short value (String -> Short)
     *
     * @param value      String Number
     * @param defaultVal Default value
     * @return Number
     */
    public static short valueOf(String value, short defaultVal) {
        try {
            return Short.valueOf(value);
        } catch (NumberFormatException ex) {
            return defaultVal;
        }
    }

    /**
     * convert a string value to a byte value (String -> Byte)
     *
     * @param value      String value
     * @param defaultVal Default value
     * @return byte
     */
    public static byte valueOf(String value, byte defaultVal) {
        try {
            return Byte.valueOf(value);
        } catch (NumberFormatException ex) {
            return defaultVal;
        }
    }
}
