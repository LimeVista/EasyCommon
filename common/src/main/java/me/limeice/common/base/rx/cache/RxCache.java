package me.limeice.common.base.rx.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Observable;
import me.limeice.common.function.Objects;
import me.limeice.common.function.cache.MemCache;
import me.limeice.common.function.cache.StorageCache;
import me.limeice.common.function.cache.StorageCacheHelper;

public class RxCache<V, BEAN> {

    public interface RxCacheHelper<V, BEAN> extends StorageCacheHelper<V, BEAN> {

        /**
         * 下载或获取数据
         *
         * @param key  唯一标识，键值
         * @param bean 数据 Bean
         * @return 下载数据，不能为空
         */
        V download(@NonNull String key, @Nullable BEAN bean);
    }

    private int duration = 0;                       // 缓存最大生命周期
    StorageCache<V, BEAN> cache;           // 内存缓存
    RxCacheHelper<V, BEAN> rxHelper; // 下载助手

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
            StorageCache<V, BEAN> cache;
            if (memCache == null) {
                if (cachePath == null)
                    cache = new StorageCache<>(context, rxCache.rxHelper);
                else
                    cache = new StorageCache<>(cachePath, rxCache.rxHelper);
            } else {
                if (cachePath == null)
                    cache = new StorageCache<>(context, rxCache.rxHelper, memCache);
                else
                    cache = new StorageCache<>(cachePath, rxCache.rxHelper, memCache);
            }
            rxCache.cache = cache;
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

    /**
     * 获得缓存机制的被观察者
     *
     * @param key 键值
     * @return 被观察者
     */
    public Observable<V> getCacheObservable(@NonNull String key) {
        return getCacheObservable(key, null);
    }

    /**
     * 获得缓存机制的被观察者
     *
     * @param key  键值
     * @param bean 数据 Bean
     * @return 被观察者
     */
    public Observable<V> getCacheObservable(@NonNull String key, @Nullable BEAN bean) {
        return Observable.create(emitter -> {
            V data;
            if ((data = cache.get(key, bean)) == null)
                emitter.onComplete();
            emitter.onNext(data);
        });
    }

    /**
     * 获得下载机制的被观察者
     *
     * @param key 键值
     * @return 被观察者
     */
    public Observable<V> getDownloadObservable(@NonNull String key) {
        return getDownloadObservable(key, null);
    }

    /**
     * 获得下载机制的被观察者
     *
     * @param key  键值
     * @param bean 数据 Bean
     * @return 被观察者
     */
    public Observable<V> getDownloadObservable(@NonNull String key, @Nullable BEAN bean) {
        return Observable.create(emitter -> {
            V value = rxHelper.download(key, bean);
            if (value == null)
                emitter.onError(new NullPointerException("Download data must be not null"));
            emitter.onNext(value);
        });
    }
}
