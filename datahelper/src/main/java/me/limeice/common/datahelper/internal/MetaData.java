package me.limeice.common.datahelper.internal;

import me.limeice.common.function.BytesUtils;

final class MetaData {

    public static final int META_SIZE = 8;

    MetaData() {
        super();
    }

    MetaData(short id) {
        this.id = id;
    }

    short id;

    short type;

    int size;

    long address;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MetaData && id == ((MetaData) obj).id;
    }

    @Override
    public int hashCode() {
        return 0xFFFF0000 + id;
    }

    static MetaData read(byte[] bs) {
        MetaData meta = new MetaData();
        meta.id = BytesUtils.getShort(bs, 0);
        meta.type = BytesUtils.getShort(bs, 2);
        meta.size = BytesUtils.getInt(bs, 4);
        return meta;
    }

    byte[] write() {
        byte[] bs = new byte[8];
        write(bs);
        return bs;
    }

    void write(byte[] bs) {
        BytesUtils.put(bs, id, 0);
        BytesUtils.put(bs, type, 2);
        BytesUtils.put(bs, size, 4);
    }

    void copyTo(MetaData meta) {
        meta.size = size;
        meta.type = type;
        meta.id = id;
        meta.address = address;
    }
}
