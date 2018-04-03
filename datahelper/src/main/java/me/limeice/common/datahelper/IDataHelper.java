package me.limeice.common.datahelper;

import java.util.List;
import java.util.Map;

public interface IDataHelper {


    byte MODE_READ_ALL = 0;

    byte MODE_READ_ID = 1;

    /**
     * 获取数据相关信息
     *
     * @return 版本号
     */
    DataHelperVerInfo getInfo();


    /**
     * 读取基本类型和数组数据
     *
     * @param id  编号
     * @param <T> 基本类型
     * @return 基本类型数据
     */
    <T> T read(short id);


    /**
     * 读取基本类型和数组数据
     *
     * @param id         编号
     * @param defaultVal 默认数据
     * @param <T>        基本类型
     * @return 基本类型数据
     */
    <T> T read(short id, T defaultVal);

    /**
     * 写入基本类型和数组数据
     *
     * @param id    编号
     * @param value 基本数据
     * @param <T>   基本数据类型
     */
    <T> void write(short id, T value);

    <T extends List> T readList(short id);


    <T extends List> T readList(short id, T inst);


    <T extends Map> T readMap(short id);


    <T extends Map> T readMap(short id, T inst);
}
