package me.limeice.common.datahelper.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.limeice.common.datahelper.DataHelperVerInfo;
import me.limeice.common.datahelper.IORuntimeException;
import me.limeice.common.datahelper.Writer;

public class WriterImpl implements Writer, DataType {

    static final String EXT = ".w_lime";

    private IDataReader mReader;

    private File mFile;

    private DataHelperVerInfo mInfo;

    private ArrayMap<MetaData, Object> writeData = new ArrayMap<>();

    @NonNull
    @Override
    public Writer put(short id, boolean value) {
        return putProxy(id, value, TYPE_BOOLEAN);
    }

    @NonNull
    @Override
    public Writer put(short id, byte value) {
        return putProxy(id, value, TYPE_BYTE);
    }

    @NonNull
    @Override
    public Writer put(short id, short value) {
        return putProxy(id, value, TYPE_SHORT);
    }

    @NonNull
    @Override
    public Writer put(short id, int value) {
        return putProxy(id, value, TYPE_INT);
    }

    @NonNull
    @Override
    public Writer put(short id, float value) {
        return putProxy(id, value, TYPE_FLOAT);
    }

    @NonNull
    @Override
    public Writer put(short id, long value) {
        return putProxy(id, value, TYPE_LONG);
    }

    @NonNull
    @Override
    public Writer put(short id, double value) {
        return putProxy(id, value, TYPE_DOUBLE);
    }

    @NonNull
    @Override
    public Writer put(short id, @NonNull String value) {
        return putProxy(id, value, TYPE_STRING);
    }

    @NonNull
    @Override
    public Writer put(short id, @NonNull boolean[] value) {
        return putProxy(id, value, TYPE_ARRAY_1_BOOLEAN);
    }

    @NonNull
    @Override
    public Writer put(short id, @NonNull byte[] value) {
        return putProxy(id, value, TYPE_ARRAY_1_BYTE);
    }

    @NonNull
    @Override
    public Writer put(short id, @NonNull short[] value) {
        return putProxy(id, value, TYPE_ARRAY_1_SHORT);
    }

    @NonNull
    @Override
    public Writer put(short id, @NonNull int[] value) {
        return putProxy(id, value, TYPE_ARRAY_1_INT);
    }

    @NonNull
    @Override
    public Writer put(short id, @NonNull float[] value) {
        return putProxy(id, value, TYPE_ARRAY_1_FLOAT);
    }

    @NonNull
    @Override
    public Writer put(short id, @NonNull long[] value) {
        return putProxy(id, value, TYPE_ARRAY_1_LONG);
    }

    @NonNull
    @Override
    public Writer put(short id, @NonNull double[] value) {
        return putProxy(id, value, TYPE_ARRAY_1_DOUBLE);
    }

    @NonNull
    @Override
    public Writer put(short id, @NonNull String[] value) {
        return putProxy(id, value, TYPE_ARRAY_1_STRING);
    }

    @Override
    public boolean commit() {
        File f = new File(mFile.getAbsoluteFile() + EXT);
        if (f.exists() && !f.delete()) return false; // 删除
        try {
            if (f.createNewFile()) return false;
            final FileOutputStream out = new FileOutputStream(f);
            byte[] bs = mInfo.createHead();
            out.write(bs);
            if (mReader instanceof MetaDataReader) {
                mReader.each(new IDataReader.Consumer() {
                    @Override
                    public void accept(@NonNull WrapData wrapData) throws IOException {
                        if (writeData.containsKey(wrapData.meta))
                            return;
                        out.write(wrapData.meta.write());
                        out.write(wrapData.bytes);
                    }
                });
            } else if (mReader instanceof AllDataReader) {
                mReader.each(new IDataReader.Consumer() {
                    @Override
                    public void accept(@NonNull WrapData wrapData) throws IOException {
                        if (writeData.containsKey(wrapData.meta))
                            return;
                        byte[] bs = DataHolder.put(wrapData.meta, wrapData.data);
                        out.write(wrapData.meta.write());
                        out.write(bs);
                    }
                });

            } else {
                if (mReader != null) throw new IllegalArgumentException("mReader type unknown.");
            }
            return true;
        } catch (IOException ex) {
            throw new IORuntimeException(ex);
        }
    }

    private Writer putProxy(short id, Object value, short type) {
        for (MetaData meta : writeData.keySet()) {
            if (meta.id == id) {
                if (meta.type != type)
                    throw new ClassCastException("the key is used, don't cast class.");
                writeData.remove(writeData);
                meta.type = type;
                writeData.put(meta, value);
            }
        }
        writeData.put(new MetaData(), value);
        return this;
    }
}
