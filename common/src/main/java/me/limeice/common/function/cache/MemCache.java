package me.limeice.common.function.cache;

import androidx.annotation.NonNull;

/**
 * 内存缓存类
 *
 * @param <V>
 */
public interface MemCache<V> extends CacheLite<V> {

    /**
     * MemCache初始化配置
     */
    interface CacheConfig<ItemType> {

        /**
         * 告知缓存改数据的占用大小
         *
         * @param key   唯一编号
         * @param value 值
         * @return 数据大小
         */
        int sizeOf(@NonNull String key, ItemType value);
    }

    /**
     * 添加到缓存（如果存在则放弃添加）
     *
     * @param key      唯一编号
     * @param item     数据
     * @param deadline 过期期限
     * @return 是否添加成功，{@code true}，添加成功，且不存在重复数据
     */
    boolean add(@NonNull String key, V item, long deadline);

    /**
     * 添加到缓存（如果存在则覆盖）
     *
     * @param key      唯一编号
     * @param item     数据
     * @param deadline 过期期限
     */
    void addOrOverlay(@NonNull String key, V item, long deadline);
}
