package me.limeice.common.datahelper;

public interface Reader {

    /**
     * 读取数据
     *
     * @param id 编号
     * @return value
     */
    boolean readBoolean(short id);

    /**
     * 读取数据
     *
     * @param id         编号
     * @param defaultVal 默认值
     * @return value
     */
    boolean readBoolean(short id, boolean defaultVal);

    /**
     * 读取数据
     *
     * @param id 编号
     * @return value
     */
    byte readByte(short id);

    /**
     * 读取数据
     *
     * @param id         编号
     * @param defaultVal 默认值
     * @return value
     */
    byte readByte(short id, byte defaultVal);

    /**
     * 读取数据
     *
     * @param id 编号
     * @return value
     */
    short readShort(short id);

    /**
     * 读取数据
     *
     * @param id         编号
     * @param defaultVal 默认值
     * @return value
     */
    short readShort(short id, short defaultVal);

    /**
     * 读取数据
     *
     * @param id 编号
     * @return value
     */
    int readInt(short id);

    /**
     * 读取数据
     *
     * @param id         编号
     * @param defaultVal 默认值
     * @return value
     */
    int readInt(short id, int defaultVal);

    /**
     * 读取数据
     *
     * @param id 编号
     * @return value
     */
    float readFloat(short id);

    /**
     * 读取数据
     *
     * @param id         编号
     * @param defaultVal 默认值
     * @return value
     */
    float readFloat(short id, float defaultVal);

    /**
     * 读取数据
     *
     * @param id 编号
     * @return value
     */
    long readLong(short id);

    long readLong(short id, long defaultVal);

    /**
     * 读取数据
     *
     * @param id 编号
     * @return value
     */
    double readDouble(short id);

    /**
     * 读取数据
     *
     * @param id         编号
     * @param defaultVal 默认值
     * @return value
     */
    double readDouble(short id, double defaultVal);

    /**
     * 读取数据
     *
     * @param id 编号
     * @return value
     */
    String readString(short id);

    /**
     * 读取数据
     *
     * @param id         编号
     * @param defaultVal 默认值
     * @return value
     */
    String readString(short id, String defaultVal);

    /**
     * 读取数组
     *
     * @param id 编号
     * @return 数组
     */
    boolean[] readBooleanArray1(short id);

    /**
     * 读取数组
     *
     * @param id 编号
     * @return 数组
     */
    byte[] readByteArray1(short id);

    /**
     * 读取数组
     *
     * @param id 编号
     * @return 数组
     */
    short[] readShortArray1(short id);

    /**
     * 读取数组
     *
     * @param id 编号
     * @return 数组
     */
    int[] readIntArray1(short id);

    /**
     * 读取数组
     *
     * @param id 编号
     * @return 数组
     */
    float[] readFloatArray1(short id);

    /**
     * 读取数组
     *
     * @param id 编号
     * @return 数组
     */
    long[] readLongArray1(short id);

    /**
     * 读取数组
     *
     * @param id 编号
     * @return 数组
     */
    double[] readDoubleArray1(short id);

    /**
     * 读取数组
     *
     * @param id 编号
     * @return 数组
     */
    String[] readStringArray1(short id);

    /**
     * 检测数据是否存在
     *
     * @param id 编号
     * @return 是否存在
     */
    boolean contains(short id);
}
