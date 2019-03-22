package me.limeice.common.base.rx.cache;

import androidx.annotation.NonNull;

import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;

/**
 * 线程安全锁
 */
interface ISafeLocker {

    class AutoDestroySemaphore {

        final AtomicInteger count = new AtomicInteger(0);   // 销毁标记
    }

    /**
     * 锁
     *
     * @param key 键
     */
    void lock(@NonNull String key);

    /**
     * 释放锁
     *
     * @param key 键
     */
    void release(@NonNull String key);

    /**
     * 根据键查询，是否已经存在
     *
     * @param key 键
     * @return 是否存在
     */
    boolean containsKey(@NonNull String key);

    /**
     * 获取
     *
     * @param key 键
     * @return 锁
     */
    default AutoDestroySemaphore get(@NonNull String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * 锁
     *
     * @param key 键
     */
    default <T> void lock(@NonNull String key, @NonNull Observable<T> observable) {
        throw new UnsupportedOperationException();
    }
}