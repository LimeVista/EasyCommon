package me.limeice.sample.rxcache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.limeice.common.base.rx.RxSchedulers;
import me.limeice.common.base.rx.cache.RxCache;
import me.limeice.common.function.CloseUtils;
import me.limeice.common.function.cache.BitmapMemCache;
import me.limeice.common.function.helper.ReaderSource;
import me.limeice.common.function.helper.WriterSource;

public class RxCacheTest {

    @Test
    public void rxCacheTestFull() throws InterruptedException {

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        RxCache<Bitmap, Object> cacheRxCache = new RxCache.Builder<Bitmap, Object>(context, new RxCache.RxCacheHelper<Bitmap, Object>() {
            @Override
            public void download(@NonNull String key, @Nullable Object o, @NonNull WriterSource writer) throws IOException {
                Log.d("下载数据", "开始下载数据");
                HttpURLConnection connection = (HttpURLConnection)
                        new URL("http://i1.hdslb.com/bfs/archive/763293ce06bf1e684ef0ea3da43ae5008d8564b8.jpg")
                                .openConnection();
                connection.connect();
                InputStream input = connection.getInputStream();
                RxCacheTest.inputSteamToOutputStream(input, writer.getOutStream());
                CloseUtils.closeIOQuietly(input);
            }

            @Nullable
            @Override
            public Bitmap read(@NonNull String key, @Nullable Object o, @NonNull ReaderSource reader) throws IOException {
                return BitmapFactory.decodeStream(reader.getInputStream());
            }
        })
                .setDuration(1000)        // 秒
                .setStorageCachePath(context.getCacheDir() + "/tmp")
                .useMemCache(new BitmapMemCache())
                .create();

        cacheRxCache.get("000001")
                .compose(RxSchedulers.io_main())
                .subscribe(bitmap -> Log.d("图片下载成功", "图片大小->" + bitmap.getByteCount()));

        Thread.sleep(6000); // 延迟用于测试
    }


    private static void inputSteamToOutputStream(InputStream in, OutputStream out) throws IOException {
        int size;
        byte[] bytes = new byte[1024];
        while ((size = in.read(bytes)) != -1) {
            out.write(bytes, 0, size);
        }
        CloseUtils.closeIOQuietly(out);
    }
}