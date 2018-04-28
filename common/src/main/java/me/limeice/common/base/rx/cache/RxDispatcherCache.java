package me.limeice.common.base.rx.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Observable;

public class RxDispatcherCache<V, BEAN> {

    public static class Dispatcher {

        static final byte NORMAL = 0;
        static final byte BROADCAST = 1;
        static final byte REARWARD = 2;

        byte mode;

        private final SafeLocker locker = new SafeLocker();

        private Dispatcher(byte mode) {
            this.mode = mode;
        }

        public static Dispatcher broadcast() {
            return new Dispatcher(BROADCAST);
        }

        public static Dispatcher rearward() {
            return new Dispatcher(REARWARD);
        }

        public static Dispatcher normal() {
            return new Dispatcher(NORMAL);
        }
    }

    private RxCache<V, BEAN> rxCache;

    private Dispatcher dispatcher;

    public RxDispatcherCache(RxCache<V, BEAN> rxCache, Dispatcher dispatcher) {
        this.rxCache = rxCache;
        this.dispatcher = dispatcher;
    }

    public Observable<V> get(@NonNull final String key, @Nullable final BEAN bean) {
        switch (dispatcher.mode) {
            case Dispatcher.NORMAL:
                return getNormalMode(key, bean);
            default:
                return getNormalMode(key, bean);
        }
    }

    private Observable<V> getNormalMode(@NonNull final String key, @Nullable final BEAN bean) {
        return Observable.fromCallable(() -> {
            try {
                dispatcher.locker.lock(key);
                V data = rxCache.cache.get(key, bean);
                if (data != null)
                    return data;
                else
                    return rxCache.rxHelper.download(key, bean);
            } finally {
                dispatcher.locker.release(key);
            }
        });
    }
}
