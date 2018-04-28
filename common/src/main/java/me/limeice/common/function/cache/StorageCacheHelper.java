package me.limeice.common.function.cache;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

public interface StorageCacheHelper<V, BEAN> {

    /**
     * 读取数据
     *
     * @param key    键
     * @param bean   数据Bean，如果在初始化的时候，你没有传递或使用Bean，该数据则为空
     * @param reader 读取通道
     * @return 读取缓存
     */
    @Nullable
    V read(@NonNull String key, @Nullable BEAN bean, @NonNull ReaderHelper reader) throws IOException;


    /**
     * 写入数据
     *
     * @param key    键
     * @param data   写入数据（不允许存在空）
     * @param bean   数据Bean，如果在初始化的时候，你没有传递或使用Bean，该数据则为空
     * @param output 写入通道
     */
    void write(@NonNull String key, @NonNull V data, @Nullable BEAN bean, @NonNull WriterHelper output) throws IOException;
}
