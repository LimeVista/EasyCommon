package me.limeice.common.function.helper;

/**
 * 磁盘缓存助手
 *
 * @param <V>    数据
 * @param <BEAN> 数据处理辅助 Bean
 */
public interface StorageCacheHelper<V, BEAN> extends
        StorageReaderHelper<V, BEAN>,
        StorageWriterHelper<V, BEAN> {
    // 磁盘缓存助手
}
