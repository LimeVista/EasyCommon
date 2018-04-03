package me.limeice.common.datahelper;

import me.limeice.common.function.BytesUtils;

@SuppressWarnings("WeakerAccess")
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
        System.arraycopy(TAG, 0, bs, 0, TAG.length);
        BytesUtils.put(bs, getVer(), TAG.length);
        BytesUtils.put(bs, getUserVer(), TAG.length + 2);
        return bs;
    }

    static DataHelperVerInfo readFormBytes(byte[] bytes) {
        if (bytes.length < MIN_SIZE)
            throw new IllegalArgumentException("byte array < " + MIN_SIZE);
        DataHelperVerInfo info = new DataHelperVerInfo();
        for (int i = 0; i < TAG.length; i++) {
            if (TAG[i] != bytes[i])
                throw new FileCorruptedException();
        }
        info.setVer(BytesUtils.getShort(bytes, TAG.length));
        info.setUserVer(BytesUtils.getShort(bytes, TAG.length + 2));
        return info;
    }
}
