package me.limeice.common.function.cache;

import android.support.annotation.NonNull;

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
}
