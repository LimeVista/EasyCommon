package me.limeice.common.datahelper.internal;

import java.io.IOException;

import me.limeice.common.datahelper.Reader;

interface IDataReader extends Reader {

    /**
     * 初始化
     */
    void init() throws IOException;
}
