package me.limeice.common.datahelper;

public interface DataHelper {

    /**
     * 获取数据存储版本号
     *
     * @return 版本号
     */
    int getVerCode();

    /**
     * 获取用户存储数据版本号
     *
     * @return 版本号
     */
    int getUserVerCode();
    
    /**
     * 读取基本类型数据
     *
     * @param id  编号
     * @param <T> 基本类型
     * @return 基本类型数据
     */
    <T> T read(short id);

    /**
     * 写入数据
     *
     * @param id    编号
     * @param value 基本数据
     * @param <T>   基本数据类型
     */
    <T> void write(short id, T value);
}
