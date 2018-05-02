package me.limeice.common.base.rx.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import me.limeice.common.function.IOUtils;
import me.limeice.common.function.helper.WriterSource;

public class RxDispatcherCache<V, BEAN> {

    /**
     * 控制策略
     */
    public static class Dispatcher {

        static final byte NORMAL = 0;
        static final byte PUBLISH = 1;
        static final byte REARWARD = 2;

        byte mode;                          // 模式
        private final ISafeLocker locker;   // 锁

        private Dispatcher(byte mode, ISafeLocker locker) {
            this.mode = mode;
            this.locker = locker;
        }

        /**
         * 分发模式，当存在还有未执行完成的被观察者，不重新执行，直接订阅
         *
         * @return 策略
         */
        public static Dispatcher publish() {
            return new Dispatcher(PUBLISH, new HotSafeLocker());
        }

        /**
         * 替代模式，暂时不支持！！
         *
         * @return 策略
         */
        @SuppressWarnings("unused")
        public static Dispatcher rearward() {
            throw new UnsupportedOperationException();
            // return new Dispatcher(REARWARD, new SafeLocker());
        }

        /**
         * 正常模式，不排除重复执行，但重复指令会被延期执行
         *
         * @return 策略
         */
        public static Dispatcher normal() {
            return new Dispatcher(NORMAL, new SafeLocker());
        }
    }

    private RxCache<V, BEAN> mRxCache;   // RxCache
    private Dispatcher mDispatcher;      // 策略

    /**
     * 请使用 {@link RxCache#buildRxDispatcherCache(Dispatcher)} 初始化
     *
     * @param rxCache    RxCache
     * @param dispatcher 控制策略
     */
    RxDispatcherCache(RxCache<V, BEAN> rxCache, Dispatcher dispatcher) {
        this.mRxCache = rxCache;
        this.mDispatcher = dispatcher;
    }

    /**
     * 获得被观察者
     *
     * @param key  键
     * @param bean 数据Bean
     * @return 被观察者
     */
    public Observable<V> get(@NonNull final String key, @Nullable final BEAN bean) {
        switch (mDispatcher.mode) {
            case Dispatcher.NORMAL:
                return getNormalMode(key, bean);

            case Dispatcher.PUBLISH:
                return getPublishMode(key, bean);

            case Dispatcher.REARWARD:
                throw new UnsupportedOperationException("暂时不支持");

            default:
                return getNormalMode(key, bean);
        }
    }

    /**
     * 获得被观察者
     *
     * @param key 键
     * @return 被观察者
     */
    public Observable<V> get(@NonNull final String key) {
        return get(key, null);
    }

    private Observable<V> getNormalMode(@NonNull final String key, @Nullable final BEAN bean) {
        return Observable.fromCallable(() -> {
            try {
                mDispatcher.locker.lock(key);
                V data = mRxCache.cache.get(key, bean);
                if (data != null)
                    return data;
                else {
                    return download(key, bean);
                }
            } finally {
                mDispatcher.locker.release(key);
            }
        });
    }

    private Observable<V> getPublishMode(@NonNull final String key, @Nullable final BEAN bean) {
        if (mDispatcher.locker.containsKey(key)) {
            return ((HotSafeLocker.AutoDestroySemaphore) mDispatcher.locker.get(key)).getObservable();
        } else {
            final Observable<V> ob = Observable.fromCallable(() -> {
                try {
                    V data = mRxCache.cache.get(key, bean);
                    if (data != null)
                        return data;
                    else
                        return download(key, bean);
                } finally {
                    mDispatcher.locker.release(key);
                }
            }).share();
            mDispatcher.locker.lock(key, ob);
            return ob;
        }
    }

    private V download(@NonNull final String key, @Nullable final BEAN bean) throws IOException {
        File cache = mRxCache.cache.getCacheFile(key);
        File cacheBak = mRxCache.cache.getCacheFileBak(key);
        if (cacheBak.exists()) {
            //noinspection ResultOfMethodCallIgnored
            cacheBak.delete();
        }
        WriterSource helper = new WriterSource(cacheBak);
        try {
            mRxCache.rxHelper.download(key, bean, helper);
            helper.close();
            if (!cache.exists() || cache.delete())
                IOUtils.moveFile(cacheBak, cache);
        } finally {
            helper.close();
        }
        return mRxCache.cache.get(key, bean);
    }
}