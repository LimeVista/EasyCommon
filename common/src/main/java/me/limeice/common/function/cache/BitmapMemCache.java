package me.limeice.common.function.cache;

import android.graphics.Bitmap;

/**
 * 图片内存缓存类
 */
public class BitmapMemCache extends AnyMemCache<Bitmap> {

    /**
     * 图片缓存
     */
    public BitmapMemCache() {
        super((key, value) -> value == null ? 0 : value.getByteCount());
    }

    /**
     * 图片缓存
     *
     * @param mCacheSize 最大内存占用
     */
    public BitmapMemCache(int mCacheSize) {
        super(mCacheSize, (key, value) -> value == null ? 0 : value.getByteCount());
    }
}