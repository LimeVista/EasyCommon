package me.limeice.common.datahelper.internal;

import java.nio.charset.Charset;

import me.limeice.common.function.BytesUtils;

final class ArraysUtils {

    public static boolean[] readBooleanArray1(byte[] bs) {
        boolean[] array = new boolean[bs.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = BytesUtils.getBoolean(bs, i);
        }
        return array;
    }

    public static short[] readShortArray1(byte[] bs) {
        short[] array = new short[bs.length >> 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = BytesUtils.getShort(bs, i << 1);
        }
        return array;
    }

    public static int[] readIntArray1(byte[] bs) {

        int[] array = new int[bs.length >> 2];
        for (int i = 0; i < array.length; i++) {
            array[i] = BytesUtils.getInt(bs, i << 2);
        }
        return array;
    }


    public static float[] readFloatArray1(byte[] bs) {
        float[] array = new float[bs.length >> 2];
        for (int i = 0; i < array.length; i++) {
            array[i] = BytesUtils.getFloat(bs, i << 2);
        }
        return array;
    }


    public static long[] readLongArray1(byte[] bs) {
        long[] array = new long[bs.length >> 3];
        for (int i = 0; i < array.length; i++) {
            array[i] = BytesUtils.getLong(bs, i << 3);
        }
        return array;
    }


    public static double[] readDoubleArray1(byte[] bs) {
        double[] array = new double[bs.length >> 3];
        for (int i = 0; i < array.length; i++) {
            array[i] = BytesUtils.getDouble(bs, i << 3);
        }
        return array;
    }


    public static String[] readStringArray1(byte[] bs) {
        int len = BytesUtils.getInt(bs);
        String[] array = new String[len];
        for (int i = 4; i < array.length; i++) {
            len = BytesUtils.getInt(bs);
            if (len == 0) continue; // isNull
            array[i] = new String(bs, i + 4, len, Charset.forName("UTF-8"));
        }
        return array;
    }
}
