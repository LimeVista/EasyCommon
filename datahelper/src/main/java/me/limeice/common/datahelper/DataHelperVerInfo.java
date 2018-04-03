package me.limeice.common.datahelper;

public class DataHelperVerInfo {

    public static final short VER_CODE = 1;

    static final byte[] TAG = {0x4C, 0x49, 0x4D, 0x45, 0x44, 0x31}; //LIMED1

    public static final short MIN_SIZE = 32;

    private static final short DEFAULT_USER_VER = 1;

    private short ver;

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
}
