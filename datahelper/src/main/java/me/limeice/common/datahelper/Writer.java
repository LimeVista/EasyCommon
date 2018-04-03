package me.limeice.common.datahelper;

import android.support.annotation.NonNull;

public interface Writer {

    @NonNull
    Writer put(short id, boolean value);

    @NonNull
    Writer put(short id, byte value);

    @NonNull
    Writer put(short id, short value);

    @NonNull
    Writer put(short id, int value);

    @NonNull
    Writer put(short id, float value);

    @NonNull
    Writer put(short id, long value);

    @NonNull
    Writer put(short id, double value);

    @NonNull
    Writer put(short id, @NonNull String value);

    @NonNull
    Writer put(short id, @NonNull boolean[] value);

    @NonNull
    Writer put(short id, @NonNull byte[] value);

    @NonNull
    Writer put(short id, @NonNull short[] value);

    @NonNull
    Writer put(short id, @NonNull int[] value);

    @NonNull
    Writer put(short id, @NonNull float[] value);

    @NonNull
    Writer put(short id, @NonNull long[] value);

    @NonNull
    Writer put(short id, @NonNull double[] value);

    @NonNull
    Writer put(short id, @NonNull String[] value);

    boolean commit();
}
