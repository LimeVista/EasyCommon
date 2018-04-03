package me.limeice.common.datahelper;

import android.support.annotation.NonNull;

public interface Writer {

    Writer put(short id, boolean value);

    Writer put(short id, byte value);

    Writer put(short id, short value);

    Writer put(short id, int value);

    Writer put(short id, float value);

    Writer put(short id, long value);

    Writer put(short id, double value);

    Writer put(short id, @NonNull String value);

    Writer put(short id, boolean[] value);

    Writer put(short id, byte[] value);

    Writer put(short id, short[] value);

    Writer put(short id, int[] value);

    Writer put(short id, float[] value);

    Writer put(short id, long[] value);

    Writer put(short id, double[] value);

    Writer put(short id, @NonNull String[] value);

    boolean commit();
}
