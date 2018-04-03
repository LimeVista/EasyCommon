package me.limeice.common.datahelper.internal;

import android.support.annotation.NonNull;

import java.io.IOException;

import me.limeice.common.datahelper.Reader;
import me.limeice.common.datahelper.internal.WrapData;

public interface IDataReader extends Reader {

    /**
     * 初始化
     */
    void init() throws IOException;

    /**
     * 遍历
     *
     * @param consumer 函数
     */
    void each(@NonNull Consumer consumer) throws IOException;

    /**
     * 遍历
     */
    interface Consumer {
        void accept(WrapData data) throws IOException;
    }
}
