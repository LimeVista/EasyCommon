package me.limeice.common.datahelper.internal;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import me.limeice.common.datahelper.FileCorruptedException;
import me.limeice.common.datahelper.IORuntimeException;
import me.limeice.common.datahelper.NotFoundDataException;
import me.limeice.common.function.BytesUtils;

import static me.limeice.common.datahelper.DataHelperVerInfo.MIN_SIZE;


public class MetaDataReader implements IDataReader, DataType {

    public MetaDataReader(@NonNull RandomAccessFile file) throws IOException {
        mFile = file;
        init();
    }

    @Override
    public boolean readBoolean(short id) {
        byte[] bs = check(id, TYPE_BOOLEAN);
        return BytesUtils.getBoolean(bs[0]);
    }

    @Override
    public boolean readBoolean(short id, boolean defaultVal) {
        try {
            return readBoolean(id);
        } catch (NotFoundDataException | FileCorruptedException ex) {
            return defaultVal;
        }
    }

    @Override
    public byte readByte(short id) {
        return check(id, TYPE_BYTE)[0];
    }

    @Override
    public byte readByte(short id, byte defaultVal) {
        try {
            return readByte(id);
        } catch (NotFoundDataException | FileCorruptedException ex) {
            return defaultVal;
        }
    }

    @Override
    public short readShort(short id) {
        byte[] bs = check(id, TYPE_SHORT);
        return BytesUtils.getShort(bs);
    }

    @Override
    public short readShort(short id, short defaultVal) {
        try {
            return readShort(id);
        } catch (NotFoundDataException | FileCorruptedException ex) {
            return defaultVal;
        }
    }

    @Override
    public int readInt(short id) {
        byte[] bs = check(id, TYPE_INT);
        return BytesUtils.getInt(bs);
    }

    @Override
    public int readInt(short id, int defaultVal) {
        try {
            return readInt(id);
        } catch (NotFoundDataException | FileCorruptedException ex) {
            return defaultVal;
        }
    }

    @Override
    public float readFloat(short id) {
        byte[] bs = check(id, TYPE_FLOAT);
        return BytesUtils.getFloat(bs);
    }

    @Override
    public float readFloat(short id, float defaultVal) {
        try {
            return readFloat(id);
        } catch (NotFoundDataException | FileCorruptedException ex) {
            return defaultVal;
        }
    }

    @Override
    public long readLong(short id) {
        byte[] bs = check(id, TYPE_LONG);
        return BytesUtils.getLong(bs);
    }

    @Override
    public long readLong(short id, long defaultVal) {
        try {
            return readLong(id);
        } catch (NotFoundDataException | FileCorruptedException ex) {
            return defaultVal;
        }
    }

    @Override
    public double readDouble(short id) {
        byte[] bs = check(id, TYPE_DOUBLE);
        return BytesUtils.getLong(bs);
    }

    @Override
    public double readDouble(short id, double defaultVal) {
        try {
            return readDouble(id);
        } catch (NotFoundDataException | FileCorruptedException ex) {
            return defaultVal;
        }
    }

    @Override
    public String readString(short id) {
        byte[] bs = check(id, TYPE_STRING);
        return new String(bs, Charset.forName("UTF-8"));
    }

    @Override
    public String readString(short id, @NonNull String defaultVal) {
        try {
            return readString(id);
        } catch (NotFoundDataException | FileCorruptedException ex) {
            return defaultVal;
        }
    }

    @Override
    public boolean[] readBooleanArray1(short id) {
        byte[] bs = check(id, TYPE_ARRAY_1_BOOLEAN);
        return ArraysUtils.readBooleanArray1(bs);
    }

    @Override
    public byte[] readByteArray1(short id) {
        return check(id, TYPE_ARRAY_1_BYTE);
    }

    @Override
    public short[] readShortArray1(short id) {
        byte[] bs = check(id, TYPE_ARRAY_1_SHORT);
        return ArraysUtils.readShortArray1(bs);
    }

    @Override
    public int[] readIntArray1(short id) {
        byte[] bs = check(id, TYPE_ARRAY_1_INT);
        return ArraysUtils.readIntArray1(bs);
    }

    @Override
    public float[] readFloatArray1(short id) {
        byte[] bs = check(id, TYPE_ARRAY_1_FLOAT);
        return ArraysUtils.readFloatArray1(bs);
    }

    @Override
    public long[] readLongArray1(short id) {
        byte[] bs = check(id, TYPE_ARRAY_1_LONG);
        return ArraysUtils.readLongArray1(bs);
    }

    @Override
    public double[] readDoubleArray1(short id) {
        byte[] bs = check(id, TYPE_ARRAY_1_DOUBLE);
        return ArraysUtils.readDoubleArray1(bs);
    }

    @Override
    public String[] readStringArray1(short id) {
        byte[] bs = check(id, TYPE_ARRAY_1_STRING);
        return ArraysUtils.readStringArray1(bs);
    }


    @Override
    public boolean contains(short id) {
        for (MetaData md : mData)
            if (md.id == id) return true;
        return false;
    }

    private byte[] check(short id, short type) {
        for (MetaData md : mData) {
            if (md.id == id) {
                if (md.type == type)
                    throw new ClassCastException("Type Error! id: " + id + ",It's type id is " + md.type);
                try {
                    byte[] bs = new byte[md.size];
                    int len;
                    mFile.seek(md.address);
                    len = mFile.read(bs);
                    if (len != md.id) throw new FileCorruptedException();
                    return bs;
                } catch (IOException e) {
                    throw new IORuntimeException(e);
                }
            }
        }
        throw new NotFoundDataException(id);
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
            mData.add(d);
        }
    }

    @Override
    public void each(@NonNull Consumer consumer) throws IOException {
        Objects.requireNonNull(consumer);
        for (MetaData md : mData) {
            WrapData wrap = new WrapData();
            wrap.meta = md;
            wrap.bytes = readBytes(md);
            consumer.accept(wrap);
        }
    }

    private byte[] readBytes(MetaData meta) {
        try {
            byte[] bs = new byte[meta.size];
            int len;
            mFile.seek(meta.address);
            len = mFile.read(bs);
            if (len != meta.id) throw new FileCorruptedException();
            return bs;
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    private RandomAccessFile mFile;

    private List<MetaData> mData = new ArrayList<>();
}