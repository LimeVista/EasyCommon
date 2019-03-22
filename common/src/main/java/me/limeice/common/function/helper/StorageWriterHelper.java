package me.limeice.common.function.helper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

/**
 * 数据写入助手
 *
 * @param <V>    数据
 * @param <BEAN> 数据处理辅助 Bean
 */
public interface StorageWriterHelper<V, BEAN> {

    /**
     * 写入数据
     *
     * @param key    键
     * @param data   写入数据（不允许存在空）
     * @param bean   数据Bean，如果在初始化的时候，你没有传递或使用Bean，该数据则为空
     * @param output 写入通道
     */
    void write(@NonNull String key, @NonNull V data, @Nullable BEAN bean, @NonNull WriterSource output) throws IOException;
}
