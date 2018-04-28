package me.limeice.common.base.rx.cache;

import android.support.annotation.NonNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

final class SafeLocker {

    private final ConcurrentMap<String, Semaphore> semaphoreLocker = new ConcurrentHashMap<>();

    void lock(@NonNull String key) {
        if (!semaphoreLocker.containsKey(key))
            semaphoreLocker.put(key, new Semaphore(1, true));

        Semaphore semaphore = semaphoreLocker.get(key);
        //semaphore.acquire();
        semaphore.acquireUninterruptibly();
    }

    void release(@NonNull String key) {
        final Semaphore semaphore = semaphoreLocker.get(key);
        if (semaphore == null)
            throw new IllegalStateException("Couldn't release semaphore.The key("
                    + key + ") isn't call lock(String) method.");
        semaphore.release();
    }

    boolean containsKey(@NonNull String key) {
        return semaphoreLocker.containsKey(key);
    }

    /**
     * 超级危险
     */
    synchronized void clearLocker() {
        semaphoreLocker.clear();
    }
}