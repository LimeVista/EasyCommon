package me.limeice.common.base.rx.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class RxCache<V, BEAN> {

    public interface DownloadHelper<V, BEAN> {

        /**
         * 下载或获取数据
         *
         * @param key  唯一标识，键值
         * @param bean 数据 Bean
         * @return 下载数据，不能为空
         */
        @NonNull
        V download(@NonNull String key, @Nullable BEAN bean);
    }
}
