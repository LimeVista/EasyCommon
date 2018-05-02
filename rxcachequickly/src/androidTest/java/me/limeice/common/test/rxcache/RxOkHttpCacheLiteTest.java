package me.limeice.common.test.rxcache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Test;

import java.io.File;

import me.limeice.common.base.rx.RxSchedulers;
import me.limeice.common.base.rx.cache.RxDispatcherCache;
import me.limeice.common.function.cache.BitmapMemCache;
import me.limeice.common.function.helper.ReaderSource;
import me.limeice.common.function.helper.StorageReaderHelper;
import me.limeice.common.rxcache.RxOkHttpCache;
import me.limeice.common.rxcache.RxOkHttpCacheLite;
import me.limeice.common.rxcache.UrlModel;
import okhttp3.OkHttpClient;

public class RxOkHttpCacheLiteTest {

    @Test
    public void testLite() throws InterruptedException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        /* 数据读取装配器 */
        StorageReaderHelper<Bitmap, String> readerHelper =
                (@NonNull String key, @Nullable String url, @NonNull ReaderSource reader) ->
                        BitmapFactory.decodeFile(reader.getFile().getAbsolutePath());

        RxOkHttpCacheLite<Bitmap> rxOkHttpCache = new RxOkHttpCacheLite
                .Builder<>(context, readerHelper)
                .useMemCache(new BitmapMemCache())      // 设置内存缓存
                .setDuration(0)                         // 缓存永不过期
                .setStorageCachePath(new File(context.getCacheDir(), "BitmapCacheLiteTmp").getAbsolutePath())   // 自定义目录
                .useCustomOkHttp(new OkHttpClient())    // 自定义OkHttp
                .create();

        rxOkHttpCache.get("bitmap_001", "http://i1.hdslb.com/bfs/archive/763293ce06bf1e684ef0ea3da43ae5008d8564b8.jpg")
                .compose(RxSchedulers.io_main()) // IO 线程切换到 UI 线程
                .subscribe(
                        bitmap -> Log.d("图片下载成功", "图片大小->" + bitmap.getByteCount()),
                        Throwable::printStackTrace
                );

        Thread.sleep(5000);
    }

    public static class Model implements UrlModel {

        @Override
        public String getUrl() {
            return "http://i1.hdslb.com/bfs/archive/763293ce06bf1e684ef0ea3da43ae5008d8564b8.jpg";
        }
    }

    @Test
    public void test() throws InterruptedException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        /* 数据读取装配器 */
        StorageReaderHelper<Bitmap, Model> readerHelper =
                (@NonNull String key, @Nullable Model url, @NonNull ReaderSource reader) ->
                        BitmapFactory.decodeFile(reader.getFile().getAbsolutePath());

        RxDispatcherCache<Bitmap, Model> rxOkHttpCache = new RxOkHttpCache
                .Builder<>(context, readerHelper)
                .useMemCache(new BitmapMemCache())      // 设置内存缓存
                .setDuration(0)                         // 缓存永不过期
                .setStorageCachePath(new File(context.getCacheDir(), "BitmapCacheLiteTmp").getAbsolutePath())   // 自定义目录
                .useCustomOkHttp(new OkHttpClient())    // 自定义OkHttp
                .create().buildRxDispatcherCache(RxDispatcherCache.Dispatcher.normal());

        for (int i = 0; i < 10; i++) {
            Model model = new Model();
            final int n = i;
            rxOkHttpCache.get("bitmap_001", model)
                    .compose(RxSchedulers.io_main()) // IO 线程切换到 UI 线程
                    .subscribe(bitmap -> {
                        Log.d("图片下载成功," + n, "图片大小->" + bitmap.getByteCount());
                    });
        }
        Thread.sleep(8000);
    }
}