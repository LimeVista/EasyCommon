package me.limeice.common.base.rx.cache;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

/**
 * 线程安全锁
 */
final class SafeLocker implements ISafeLocker {

    // private final ConcurrentHashMap<String, AutoDestroySemaphore> semaphoreLocker = new ConcurrentHashMap<>();

    private final HashMap<String, AutoDestroySemaphore> semaphoreLocker = new HashMap<>();

    private final byte[] lock = new byte[0];

    class AutoDestroySemaphore extends ISafeLocker.AutoDestroySemaphore {

        final Semaphore semaphore = new Semaphore(1, true); // 信号量
    }

    /**
     * 锁
     *
     * @param key 键
     */
    public void lock(@NonNull String key) {
        AutoDestroySemaphore semaphore;
        synchronized (lock) {
            if (!semaphoreLocker.containsKey(key)) {
                semaphore = new AutoDestroySemaphore();
                semaphoreLocker.put(key, semaphore);
            } else
                semaphore = semaphoreLocker.get(key);
            semaphore.count.incrementAndGet();  // 引用计数+1
        }
        // semaphore.acquire();
        semaphore.semaphore.acquireUninterruptibly();
    }

    /**
     * 释放锁
     *
     * @param key 简直
     */
    public void release(@NonNull String key) {
        final AutoDestroySemaphore semaphore = semaphoreLocker.get(key);
        if (semaphore == null)
            throw new IllegalStateException("Couldn't release semaphore.The key("
                    + key + ") isn't call lock(String) method.");
        semaphore.semaphore.release();
        synchronized (lock) {
            if (semaphore.count.decrementAndGet() <= 0) // 引用计数-1
                semaphoreLocker.remove(key); // 当引用计数为0时，删除引用
        }
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