package me.limeice.common.function.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 内存缓存类
 *
 * @param <V>
 */
public interface Cache<V, BEAN> extends CacheLite<V> {

    /**
     * 添加到缓存（如果存在则放弃添加）
     *
     * @param key  唯一编号
     * @param item 数据
     * @param bean 数据Bean(用于辅助数据操作)
     * @return 是否添加成功，{@code true}，添加成功，且不存在重复数据
     */
    boolean add(@NonNull String key, V item, @Nullable BEAN bean);

    /**
     * 添加到缓存（如果存在则覆盖）
     *
     * @param key  唯一编号
     * @param bean 数据Bean(用于辅助数据操作)
     * @param item 数据
     */
    void addOrOverlay(@NonNull String key, V item, @Nullable BEAN bean);

    /**
     * 获得数据
     *
     * @param key  唯一编号
     * @param bean 数据Bean(用于辅助数据操作)
     * @return 数据，如果 {@code return null} , 则数据不存在
     */
    @Nullable
    V get(@NonNull final String key, @Nullable BEAN bean);

}
