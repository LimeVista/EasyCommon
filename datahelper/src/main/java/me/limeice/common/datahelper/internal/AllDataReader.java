package me.limeice.common.datahelper.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

import static me.limeice.common.datahelper.DataHelperVerInfo.MIN_SIZE;

public class AllDataReader implements IDataReader, DataType {


    private RandomAccessFile mFile;

    private ArrayMap<MetaData, Object> mData = new ArrayMap<>();

    public AllDataReader(@NonNull RandomAccessFile file) throws IOException {
        mFile = file;
        init();
    }

    @Override
    public boolean readBoolean(short id) {
        return (boolean) mData.get(new MetaData(id));
    }

    @Override
    public boolean readBoolean(short id, boolean defaultVal) {
        Boolean b = (Boolean) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public byte readByte(short id) {
        return (byte) mData.get(new MetaData(id));
    }

    @Override
    public byte readByte(short id, byte defaultVal) {
        Byte b = (Byte) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public short readShort(short id) {
        return (short) mData.get(new MetaData(id));
    }

    @Override
    public short readShort(short id, short defaultVal) {
        Short b = (Short) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public int readInt(short id) {
        return (int) mData.get(new MetaData(id));
    }

    @Override
    public int readInt(short id, int defaultVal) {
        Integer b = (Integer) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public float readFloat(short id) {
        return (float) mData.get(new MetaData(id));
    }

    @Override
    public float readFloat(short id, float defaultVal) {
        Float b = (Float) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public long readLong(short id) {
        return (long) mData.get(new MetaData(id));
    }

    @Override
    public long readLong(short id, long defaultVal) {
        Long b = (Long) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public double readDouble(short id) {
        return (double) mData.get(new MetaData(id));
    }

    @Override
    public double readDouble(short id, double defaultVal) {
        Double b = (Double) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public String readString(short id) {
        return (String) mData.get(new MetaData(id));
    }

    @Override
    public String readString(short id, String defaultVal) {
        String b = (String) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public boolean[] readBooleanArray1(short id) {
        return new boolean[0];
    }

    @Override
    public byte[] readByteArray1(short id) {
        return new byte[0];
    }

    @Override
    public short[] readShortArray1(short id) {
        return new short[0];
    }

    @Override
    public int[] readIntArray1(short id) {
        return new int[0];
    }

    @Override
    public float[] readFloatArray1(short id) {
        return new float[0];
    }

    @Override
    public long[] readLongArray1(short id) {
        return new long[0];
    }

    @Override
    public double[] readDoubleArray1(short id) {
        return new double[0];
    }

    @Override
    public String[] readStringArray1(short id) {
        return new String[0];
    }

    @Override
    public boolean contains(short id) {
        return mData.containsKey(new MetaData(id));
    }

    @Override
    public void init() throws IOException {
        long seek = MIN_SIZE - 1;
        mFile.seek(seek);
        byte[] bs = new byte[8];
        int len;
        mData.clear();
        while ((len = mFile.read(bs)) == 8) {
            seek += len;
            MetaData d = MetaData.read(bs);
            d.address = seek;
            if (!reader(d)) break;
        }
    }

    @Override
    public void each(@NonNull Consumer consumer) throws IOException {
        Objects.requireNonNull(consumer);
        for (Map.Entry<MetaData, Object> entry : mData.entrySet()) {
            WrapData wrap = new WrapData();
            wrap.data = entry.getValue();
            wrap.meta = entry.getKey();
            consumer.accept(wrap);
        }
    }

    private boolean reader(MetaData meta) throws IOException {
        byte[] bs = new byte[meta.size];
        int len = mFile.read(bs);
        if (len <= meta.size) return false;
        mData.put(meta, DataHolder.get(meta, bs));
        return true;
    }

}
