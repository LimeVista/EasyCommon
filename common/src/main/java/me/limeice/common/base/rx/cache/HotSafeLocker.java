package me.limeice.common.base.rx.cache;

import android.support.annotation.NonNull;

import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;

/**
 * 线程安全锁
 */
final class HotSafeLocker implements ISafeLocker {

    private final ConcurrentHashMap<String, AutoDestroySemaphore> semaphoreLocker = new ConcurrentHashMap<>();

    /**
     * 自动回收信号量
     */
    static class AutoDestroySemaphore extends ISafeLocker.AutoDestroySemaphore {

        final Observable observable;

        <T> AutoDestroySemaphore(Observable<T> observable) {
            this.observable = observable;
        }

        public <T> Observable<T> getObservable() {
            //noinspection unchecked
            return observable;
        }
    }

    /**
     * 锁
     *
     * @param key 键
     */
    public void lock(@NonNull String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取值
     *
     * @param key 键
     * @return 锁
     */
    public AutoDestroySemaphore get(@NonNull String key) {
        return semaphoreLocker.get(key);
    }

    /**
     * 释放锁
     *
     * @param key 键
     */
    public synchronized void release(@NonNull String key) {
        final AutoDestroySemaphore semaphore = semaphoreLocker.get(key);
        if (semaphore != null)
            semaphoreLocker.remove(key, semaphore);
    }

    /**
     * 根据键查询，是否已经存在
     *
     * @param key 键
     * @return 是否存在
     */
    @Override
    public synchronized boolean containsKey(@NonNull String key) {
        return semaphoreLocker.containsKey(key);
    }

    /**
     * 锁
     *
     * @param key        键
     * @param observable 被观察者
     */
    @Override
    public synchronized <T> void lock(@NonNull String key, @NonNull Observable<T> observable) {
        semaphoreLocker.put(key, new AutoDestroySemaphore(observable));
    }
}