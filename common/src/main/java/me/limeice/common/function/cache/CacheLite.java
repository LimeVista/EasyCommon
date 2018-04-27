package me.limeice.common.function.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 内存缓存类
 *
 * @param <V>
 */
public interface CacheLite<V> {

    /**
     * 添加到缓存（如果存在则放弃添加）
     *
     * @param key  唯一编号
     * @param item 数据
     * @return 是否添加成功，{@code true}，添加成功，且不存在重复数据
     */
    boolean add(@NonNull String key, V item);

    /**
     * 添加到缓存（如果存在则覆盖）
     *
     * @param key  唯一编号
     * @param item 数据
     */
    void addOrOverlay(@NonNull String key, V item);

    /**
     * 获得数据
     *
     * @param key 唯一编号
     * @return 数据，如果 {@code return null} , 则数据不存在
     */
    @Nullable
    V get(@NonNull final String key);

    /**
     * 很据编号获取缓存
     *
     * @param key 唯一编号
     */
    void remove(@NonNull final String key);

    /**
     * 清空缓存
     */
    void clean();

    /**
     * 生命周期（内存缓存在内存中最大超时，超出时间后，删除），单位：秒
     *
     * @return 生命周期
     */
    int getDuration();

    /**
     * 设置生命周期
     *
     * @param duration 维持时间：单位秒
     */
    void setDuration(int duration);

    /**
     * 清除超生命周期（过期）数据
     */
    void cleanInvalid();
}
