package me.limeice.common.base.rx.cache;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import me.limeice.common.function.IOUtils;
import me.limeice.common.function.Objects;
import me.limeice.common.function.cache.MemCache;
import me.limeice.common.function.cache.StorageCache;
import me.limeice.common.function.helper.ReaderSource;
import me.limeice.common.function.helper.StorageCacheHelper;
import me.limeice.common.function.helper.StorageReaderHelper;
import me.limeice.common.function.helper.WriterSource;

public class RxCache<V, BEAN> {

    public interface RxCacheHelper<V, BEAN> extends StorageReaderHelper<V, BEAN> {

        /**
         * 下载或获取数据
         *
         * @param key    唯一标识，键
         * @param bean   数据 Bean
         * @param writer 写入工具
         * @throws IOException IO 异常
         */
        void download(@NonNull String key, @Nullable BEAN bean, @NonNull WriterSource writer) throws IOException;
    }

    protected int duration = 0;                     // 缓存最大生命周期
    StorageCache<V, BEAN> cache;                    // 内存缓存
    RxCacheHelper<V, BEAN> rxHelper;                // 下载助手


    protected RxCache() {
        // 保护
    }

    public static class Builder<V, BEAN> {

        private Context context;
        private RxCache<V, BEAN> rxCache;
        private String cachePath;
        private MemCache<V> memCache;

        public Builder(@NonNull final Context context,
                       @NonNull RxCacheHelper<V, BEAN> rxCacheHelper) {
            this.context = context.getApplicationContext();
            rxCache = new RxCache<>();
            rxCache.rxHelper = Objects.requireNonNull(rxCacheHelper);
        }

        /**
         * 这是过期期限，最大生命周期
         *
         * @param duration 生命周期，秒
         * @return 链式编程
         */
        public Builder<V, BEAN> setDuration(int duration) {
            rxCache.duration = duration;
            return this;
        }

        /**
         * 自定义缓存路径
         *
         * @param path 文件夹路径
         * @return 链式编程
         */
        public Builder<V, BEAN> setStorageCachePath(@NonNull String path) {
            this.cachePath = Objects.requireNonNull(path);
            return this;
        }

        /**
         * 创建磁盘缓存
         *
         * @return 链式编程
         */
        public RxCache<V, BEAN> create() {
            if (cachePath == null)
                rxCache.initCache(new File(context.getCacheDir(), StorageCache.CACHE_DIR), memCache);
            else {
                rxCache.initCache(new File(cachePath), memCache);
            }
            rxCache.cache.setDuration(rxCache.duration);
            return rxCache;
        }

        /**
         * 使用内存缓存
         *
         * @param memCache 内存缓存
         * @return 链式编程
         */
        public Builder<V, BEAN> useMemCache(@NonNull MemCache<V> memCache) {
            this.memCache = Objects.requireNonNull(memCache);
            return this;
        }
    }

    public Observable<V> get(@NonNull String key) {
        return Observable.concat(getCacheObservable(key), getDownloadObservable(key))
                .firstOrError()
                .toObservable();
    }

    public Observable<V> get(@NonNull String key, @Nullable BEAN bean) {
        return Observable.concat(getCacheObservable(key, bean), getDownloadObservable(key, bean))
                .firstOrError()
                .toObservable();
    }

    /**
     * 获得缓存机制的被观察者
     *
     * @param key 键
     * @return 被观察者
     */
    public Observable<V> getCacheObservable(@NonNull String key) {
        return getCacheObservable(key, null);
    }

    /**
     * 获得缓存机制的被观察者
     *
     * @param key  键
     * @param bean 数据 Bean
     * @return 被观察者
     */
    public Observable<V> getCacheObservable(@NonNull String key, @Nullable BEAN bean) {
        return Observable.create(emitter -> {
            V data;
            if ((data = cache.get(key, bean)) == null)
                emitter.onComplete();
            else
                emitter.onNext(data);
        });
    }

    /**
     * 获得下载机制的被观察者
     *
     * @param key 键
     * @return 被观察者
     */
    public Observable<V> getDownloadObservable(@NonNull String key) {
        return getDownloadObservable(key, null);
    }

    /**
     * 获得下载机制的被观察者
     *
     * @param key  键
     * @param bean 数据 Bean
     * @return 被观察者
     */
    public Observable<V> getDownloadObservable(@NonNull String key, @Nullable BEAN bean) {
        return Observable.create(emitter -> {
            File cache = this.cache.getCacheFile(key);
            File cacheBak = this.cache.getCacheFileBak(key);
            if (cacheBak.exists()) {
                //noinspection ResultOfMethodCallIgnored
                cacheBak.delete();
            }
            WriterSource helper = new WriterSource(cacheBak);
            try {
                rxHelper.download(key, bean, helper);
                helper.close();
                if (!cache.exists() || cache.delete())
                    IOUtils.moveFile(cacheBak, cache);
            } finally {
                helper.close();
            }
            V value = this.cache.get(key, bean);
            if (value == null)
                emitter.onError(new NullPointerException("cache is null"));
            else
                emitter.onNext(value);
        });
    }

    /**
     * 建造为带策略模式的 RxCache
     *
     * @param dispatcher 策略
     * @return {@link RxDispatcherCache}
     */
    public RxDispatcherCache<V, BEAN> buildRxDispatcherCache(RxDispatcherCache.Dispatcher dispatcher) {
        Objects.requireNonNull(dispatcher);
        return new RxDispatcherCache<>(this, dispatcher);
    }

    /**
     * 初始化Cache
     *
     * @param file     文件
     * @param memCache 内存Cache,如果不使用，则为空
     */
    protected void initCache(File file, MemCache<V> memCache) {
        cache = new RxStorageCache(file, memCache);
    }

    private class RxStorageCache extends StorageCache<V, BEAN> {

        RxStorageCache(File file, MemCache<V> memCache) {
            super(file, new StorageCacheHelper<V, BEAN>() {

                @Override
                public void write(@NonNull String key, @NonNull V data, @Nullable BEAN bean, @NonNull WriterSource output) {
                    // 什么都不做
                }

                @Nullable
                @Override
                public V read(@NonNull String key, @Nullable BEAN bean, @NonNull ReaderSource reader) throws IOException {
                    return rxHelper.read(key, bean, reader);
                }
            }, memCache);
        }

        /**
         * 添加到缓存（如果存在则放弃添加）
         *
         * @param key  唯一编号
         * @param item 数据
         * @param bean 数据Bean(用于辅助数据操作)
         * @return 是否添加成功，{@code true}，添加成功，且不存在重复数据
         */
        @Override
        public boolean add(@NonNull String key, V item, @Nullable BEAN bean) {
            return memCache != null && memCache.get(key) == null && memCache.add(key, item);
        }

        /**
         * 添加到缓存（如果存在则覆盖）
         *
         * @param key  唯一编号
         * @param item 数据
         * @param bean 数据Bean(用于辅助数据操作)
         */
        @Override
        public void addOrOverlay(@NonNull String key, V item, @Nullable BEAN bean) {
            if (memCache != null)
                memCache.addOrOverlay(key, item);
        }
    }

    protected void setRxHelper(RxCacheHelper<V, BEAN> rxHelper) {
        Objects.requireNonNull(rxHelper);
        this.rxHelper = rxHelper;
    }

    protected StorageCache<V, BEAN> getCache() {
        return this.cache;
    }
}
