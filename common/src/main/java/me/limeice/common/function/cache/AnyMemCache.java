package me.limeice.common.function.cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.LruCache;

/**
 * 内存缓存类
 *
 * @param <V> V
 */
public class AnyMemCache<V> implements MemCache<V> {

    /**
     * 主存
     */
    private LruCache<String, WrapCache> mCache;

    /**
     * 读取过滤器
     */
    private DataFilter<V> mGetDataFilter = null;

    /**
     * 数据过滤器
     *
     * @param <V> V
     */
    public interface DataFilter<V> {
        /**
         * 判定数据是否可用
         *
         * @param key   键
         * @param value 值
         * @return 是否可用
         */
        boolean available(String key, V value);
    }

    /**
     * 持续时间，毫秒
     */
    private int duration = 0;

    /**
     * 内存缓存
     *
     * @param config 配置缓存
     */
    public AnyMemCache(@NonNull CacheConfig<V> config) {
        //获取系统分配给应用的总内存大小,设置内存缓存占用八分之一
        this((int) (Runtime.getRuntime().maxMemory() >>> 3), config);
    }

    /**
     * 内存缓存
     *
     * @param cacheSize 缓存大小
     * @param config    配置缓存
     */
    public AnyMemCache(int cacheSize, @NonNull CacheConfig<V> config) {
        //创建缓存
        mCache = new LruCache<String, WrapCache>(cacheSize) {
            @Override
            protected int sizeOf(String key, WrapCache value) {
                return config.sizeOf(key, value.value);
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
                mCache.put(key, new WrapCache(item));
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
                mCache.put(key, new WrapCache(item));
            } else {
                remove(key);
                mCache.put(key, new WrapCache(item));
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
        WrapCache cache = mCache.get(key);
        if (cache == null)
            return null;
        if ((duration == 0 || System.currentTimeMillis() <= cache.deadline)
                && (mGetDataFilter == null || mGetDataFilter.available(key, cache.value)))
            return cache.value;
        remove(key);
        return null;
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

    /**
     * 生命周期（内存缓存在内存中最大超时，超出时间后，删除），单位：秒
     *
     * @return 生命周期
     */
    @Override
    public int getDuration() {
        return duration;
    }

    /**
     * 设置生命周期
     *
     * @param sec 维持时间：单位秒
     */
    @Override
    public void setDuration(int sec) {
        this.duration = sec <= 0 ? 0 : sec * 1000;
    }

    /**
     * 清除超生命周期（过期）数据
     */
    @Override
    public synchronized void cleanInvalid() {
        throw new UnsupportedOperationException("MemCache Not support");
    }

    /**
     * 添加到缓存（如果存在则放弃添加）
     *
     * @param key      唯一编号
     * @param item     数据
     * @param deadline 过期期限
     * @return 是否添加成功，{@code true}，添加成功，且不存在重复数据
     */
    @Override
    public boolean add(@NonNull String key, V item, long deadline) {
        if (item != null) {
            if (get(key) == null) {
                mCache.put(key, new WrapCache(item, deadline));
                return true;
            }
        }
        return false;
    }

    /**
     * 添加到缓存（如果存在则覆盖）
     *
     * @param key      唯一编号
     * @param item     数据
     * @param deadline 过期期限
     */
    @Override
    public void addOrOverlay(@NonNull String key, V item, long deadline) {
        if (item != null) {
            if (get(key) == null) {
                mCache.put(key, new WrapCache(item, deadline));
            } else {
                remove(key);
                mCache.put(key, new WrapCache(item, deadline));
            }
        }
    }

    /**
     * 设置内存数据过滤器
     *
     * @param getDataFilter 内存数据过滤器
     */
    public void setGetDataFilter(@Nullable DataFilter<V> getDataFilter) {
        this.mGetDataFilter = getDataFilter;
    }

    private class WrapCache {

        /**
         * 值，删除时间
         */
        V value;

        /**
         * 删除时间
         */
        long deadline = 0;

        WrapCache(V value) {
            this.value = value;
            if (duration > 0) {
                deadline = System.currentTimeMillis() + duration;
            }
        }

        WrapCache(V value, long deadline) {
            this.value = value;
            this.deadline = deadline;
        }
    }
}