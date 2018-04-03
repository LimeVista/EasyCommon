package me.limeice.common.datahelper;

import java.io.IOException;

import io.reactivex.annotations.NonNull;

public interface IDataHelper {

    //////////////////////////////////////////

    byte MODE_READ_ALL = 0;     // 完整读取模式

    byte MODE_READ_ID = 1;      // 仅读取基本信息

    //////////////////////////////////////////

    byte STATE_EXIST = 1;

    byte STATE_NOT_EXIST = 2;

    byte STATE_TYPE_ERROR = 3;

    byte STATE_UNKNOWN = 0;

    byte STATE_NOT_LOAD = -1;

    byte STATE_OK = 4;

    //////////////////////////////////////////

    /**
     * 获取数据相关信息
     *
     * @return 版本号
     */
    DataHelperVerInfo getInfo();

    /**
     * 创建读取者
     *
     * @return 读取者
     */
    @NonNull
    Reader reader();

    /**
     * 打开编辑者
     *
     * @return 编辑者
     */

    @NonNull
    Writer writer();

    /**
     * 重新加载
     *
     * @throws IOException File I/O Exception
     */
    void reload() throws IOException;
}
