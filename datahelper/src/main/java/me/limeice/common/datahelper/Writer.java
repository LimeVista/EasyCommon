package me.limeice.common.datahelper;

import android.support.annotation.NonNull;

public interface Writer {

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, boolean value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, byte value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, short value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, int value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, float value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, long value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, double value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, @NonNull String value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, @NonNull boolean[] value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, @NonNull byte[] value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, @NonNull short[] value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, @NonNull int[] value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, @NonNull float[] value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, @NonNull long[] value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, @NonNull double[] value);

    /**
     * 载入数据
     *
     * @param id    编号
     * @param value 值
     * @return 链式编程
     */
    @NonNull
    Writer put(short id, @NonNull String[] value);

    /**
     * 提交操作（基本满足事务原子性）
     *
     * @return {@code true} 成功，{@code false}失败
     */
    boolean commit();
}
