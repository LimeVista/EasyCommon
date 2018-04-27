package me.limeice.common.function.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.LruCache;

/**
 * 内存缓存类
 *
 * @param <V>
 */
public class AnyMemCache<V> implements MemCache<V> {

    private LruCache<String, V> mCache;

    /**
     * 内存缓存
     *
     * @param config 配置缓存
     */
    public AnyMemCache(@NonNull CacheConfig<V> config) {

        //获取系统分配给应用的总内存大小,设置内存缓存占用八分之一
        int mCacheSize = (int) (Runtime.getRuntime().maxMemory() >>> 3);

        //创建缓存
        mCache = new LruCache<String, V>(mCacheSize) {
            @Override
            protected int sizeOf(String key, V value) {
                return config.sizeOf(key, value);
            }
        };
    }

    /**
     * 内存缓存
     *
     * @param cacheSize 缓存大小
     * @param config    配置缓存
     */
    public AnyMemCache(int cacheSize, @NonNull CacheConfig<V> config) {
        //创建缓存
        mCache = new LruCache<String, V>(cacheSize) {
            @Override
            protected int sizeOf(String key, V value) {
                return config.sizeOf(key, value);
            }
        };
    }

    /**
     * 添加到缓存（如果存在则放弃添加）
     *
     * @param key  唯一编号
     * @param item 数据
     * @return 是否添加成功，{@code true}，添加成功，且不存在重复数据
     */
    @Override
    public boolean add(@NonNull final String key, V item) {
        if (item != null) {
            if (get(key) == null) {
                mCache.put(key, item);
                return true;
            }
        }
        return false;
    }

    /**
     * 添加到缓存（如果存在则覆盖）
     *
     * @param key  唯一编号
     * @param item 数据
     */
    @Override
    public void addOrOverlay(@NonNull final String key, V item) {
        if (item != null) {
            if (get(key) == null) {
                mCache.put(key, item);
            } else {
                remove(key);
                mCache.put(key, item);
            }
        }
    }

    /**
     * 获得数据
     *
     * @param key 唯一编号
     * @return 数据，如果 {@code return null} , 则数据不存在
     */
    @Nullable
    @Override
    public V get(@NonNull final String key) {
        return mCache.get(key);
    }

    /**
     * 从内存缓存中移除
     *
     * @param key 主键
     */
    @Override
    public void remove(@NonNull final String key) {
        mCache.remove(key);
    }

    /**
     * 清空缓存
     */
    @Override
    public void clean() {
        mCache.evictAll();
    }

}
