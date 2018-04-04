package me.limeice.common.datahelper.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
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
        Boolean val = returnCheckNull(id);
        return val == null ? false : val;
    }

    @Override
    public boolean readBoolean(short id, boolean defaultVal) {
        Boolean b = (Boolean) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public byte readByte(short id) {
        Byte val = returnCheckNull(id);
        return val == null ? 0 : val;
    }

    @Override
    public byte readByte(short id, byte defaultVal) {
        Byte b = (Byte) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public short readShort(short id) {
        Short val = returnCheckNull(id);
        return val == null ? 0 : val;
    }

    @Override
    public short readShort(short id, short defaultVal) {
        Short b = (Short) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public int readInt(short id) {
        Integer val = returnCheckNull(id);
        return val == null ? 0 : val;
    }

    @Override
    public int readInt(short id, int defaultVal) {
        Integer b = (Integer) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public float readFloat(short id) {
        Float val = returnCheckNull(id);
        return val == null ? 0 : val;
    }

    @Override
    public float readFloat(short id, float defaultVal) {
        Float b = (Float) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public long readLong(short id) {
        Long val = returnCheckNull(id);
        return val == null ? 0 : val;
    }

    @Override
    public long readLong(short id, long defaultVal) {
        Long b = (Long) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public double readDouble(short id) {
        Double val = returnCheckNull(id);
        return val == null ? 0 : val;
    }

    @Override
    public double readDouble(short id, double defaultVal) {
        Double b = (Double) mData.get(new MetaData());
        return b == null ? defaultVal : b;
    }

    @Override
    public String readString(short id) {
        return returnCheckNull(id);
    }

    @Override
    public String readString(short id, String defaultVal) {
        String val = returnCheckNull(id);
        return val == null ? defaultVal : val;
    }

    @Override
    public boolean[] readBooleanArray1(short id) {
        return returnCheckNull(id);
    }

    @Override
    public byte[] readByteArray1(short id) {
        return returnCheckNull(id);
    }

    @Override
    public short[] readShortArray1(short id) {
        return returnCheckNull(id);
    }

    @Override
    public int[] readIntArray1(short id) {
        return returnCheckNull(id);
    }

    @Override
    public float[] readFloatArray1(short id) {
        return returnCheckNull(id);
    }

    @Override
    public long[] readLongArray1(short id) {
        return returnCheckNull(id);
    }

    @Override
    public double[] readDoubleArray1(short id) {
        return returnCheckNull(id);
    }

    @Override
    public String[] readStringArray1(short id) {
        return returnCheckNull(id);
    }

    @Override
    public boolean contains(short id) {
        return mData.containsKey(new MetaData(id));
    }

    @Override
    public <T> List<T> readList(short id) {
        return returnCheckNull(id);
    }

    @Override
    public <T> List<T> readList(short id, List<T> inst) {
        return returnCheckNull(id);
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

    @SuppressWarnings("unchecked")
    private <T> T returnCheckNull(short id) {
        Object ob = mData.get(new MetaData(id));
        return ob == null ? null : (T) ob;
    }
}
