package me.limeice.common.function;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 字节流处理工具
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2018/03/16
 *     desc  : Bytes工具类
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
public final class BytesUtils {

    private BytesUtils() {
        throw new UnsupportedOperationException("Don't instantiate...");
    }

    /**
     * Convert byte array to hex string
     *
     * @param bytes byte array, eg:[0x1A,0x2C,0x3B]
     * @return hex string, eg: 1A2C3B
     */
    @NonNull
    public static String toHexString(@NonNull byte[] bytes) {
        if (bytes.length == 0)
            return "";
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            String hv = Integer.toHexString(b & 0xFF);
            if (hv.length() < 2)
                builder.append(0);
            builder.append(hv);
        }
        return builder.toString();
    }

    /**
     * Convert byte to hex value
     *
     * @param b byte, eg: 8
     * @return hex value, eg: 0x08
     */
    @NonNull
    public static String convert(byte b) {
        String hv = Integer.toHexString(b & 0xFF);
        return hv.length() < 2 ? ("0x0" + hv) : ("0x" + hv);
    }

    /**
     * Convert byte array to hex values
     *
     * @param bytes byte array, eg: [8,1]
     * @return hex value, eg: [0x08, 0x01]
     */
    @NonNull
    public static String convert(@NonNull final byte[] bytes) {
        StringBuilder builder = new StringBuilder("[");
        int i = 0;
        for (; i < bytes.length - 1; i++)
            builder.append(convert(bytes[i])).append(", ");

        return builder.append(convert(bytes[i])).append(']').toString();
    }

    /**
     * Convert hex string to byte array
     *
     * @param hexString hex string eg: 1A2C3B
     * @return byte array eg:[0x1A,0x2C,0x3B]
     */
    @Nullable
    public static byte[] hexStringToBytes(@Nullable String hexString) {
        if (hexString == null || hexString.length() == 0)
            return null;
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i << 1;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte 0 1 2 3 4 5 6 7 8 9 A B C D E F
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
