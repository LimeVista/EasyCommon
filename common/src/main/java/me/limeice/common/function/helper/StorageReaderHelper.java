package me.limeice.common.function.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * 数据读取助手
 *
 * @param <V>    数据
 * @param <BEAN> 数据处理辅助 Bean
 */
public interface StorageReaderHelper<V, BEAN> {

    /**
     * 读取数据
     *
     * @param key    键
     * @param bean   数据辅助处理 Bean，如果在初始化的时候，你没有传递或使用Bean，该数据则为空
     * @param reader 读取通道
     * @return 读取缓存
     */
    @Nullable
    V read(@NonNull String key, @Nullable BEAN bean, @NonNull ReaderSource reader) throws IOException;
}
