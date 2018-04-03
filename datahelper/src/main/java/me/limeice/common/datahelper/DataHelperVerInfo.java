package me.limeice.common.datahelper;

import me.limeice.common.function.BytesUtils;

public class DataHelperVerInfo {

    public static final short VER_CODE = 1;

    public static final byte[] TAG = {0x4C, 0x49, 0x4D, 0x45, 0x44, 0x31}; //LIMED1

    public static final short MIN_SIZE = 32;

    private static final short DEFAULT_USER_VER = 1;

    private short ver = VER_CODE;

    private short userVer = DEFAULT_USER_VER;

    public short getVer() {
        return ver;
    }

    void setVer(short ver) {
        this.ver = ver;
    }

    public short getUserVer() {
        return userVer;
    }

    public void setUserVer(short userVer) {
        this.userVer = userVer;
    }

    public byte[] createHead() {
        byte[] bs = new byte[MIN_SIZE];
        System.arraycopy(DataHelperVerInfo.TAG, 0, bs, 0, DataHelperVerInfo.TAG.length);
        BytesUtils.put(bs, getVer(), DataHelperVerInfo.TAG.length);
        BytesUtils.put(bs, getUserVer(), DataHelperVerInfo.TAG.length + 2);
        return bs;
    }
}
