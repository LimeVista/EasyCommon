package me.limeice.common.base.rx.cache;

import android.support.annotation.NonNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * 线程安全锁
 */
final class SafeLocker implements ISafeLocker {

    private final ConcurrentHashMap<String, AutoDestroySemaphore> semaphoreLocker = new ConcurrentHashMap<>();

    class AutoDestroySemaphore extends ISafeLocker.AutoDestroySemaphore {

        final Semaphore semaphore = new Semaphore(1, true); // 信号量
    }

    /**
     * 锁
     *
     * @param key 键
     */
    public synchronized void lock(@NonNull String key) {
        if (!semaphoreLocker.containsKey(key))
            semaphoreLocker.put(key, new AutoDestroySemaphore());

        AutoDestroySemaphore semaphore = semaphoreLocker.get(key);
        semaphore.count.incrementAndGet();  // 引用计数+1
        // semaphore.acquire();
        semaphore.semaphore.acquireUninterruptibly();
    }

    /**
     * 释放锁
     *
     * @param key 简直
     */
    public synchronized void release(@NonNull String key) {
        final AutoDestroySemaphore semaphore = semaphoreLocker.get(key);
        if (semaphore == null)
            throw new IllegalStateException("Couldn't release semaphore.The key("
                    + key + ") isn't call lock(String) method.");
        semaphore.semaphore.release();
        if (semaphore.count.decrementAndGet() <= 0) // 引用计数-1
            semaphoreLocker.remove(key, semaphore); // 当引用计数为0时，删除引用
    }

    /**
     * 根据键查询，是否已经存在
     *
     * @param key 键
     * @return 是否存在
     */
    @Override
    public boolean containsKey(@NonNull String key) {
        return semaphoreLocker.containsKey(key);
    }
}