package me.limeice.common.datahelper.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import me.limeice.common.datahelper.DataHelperVerInfo;
import me.limeice.common.datahelper.IORuntimeException;
import me.limeice.common.datahelper.Writer;
import me.limeice.common.function.CloseUtils;

public class WriterImpl implements Writer, DataType {

    private IDataReader mReader;

    private File mFile;

    private DataHelperVerInfo mInfo;

    private ArrayMap<MetaData, Object> writeData = new ArrayMap<>();

    public WriterImpl(IDataReader reader, File file, DataHelperVerInfo info) {
        this.mReader = reader;
        this.mFile = file;
        this.mInfo = info;
    }

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
    public synchronized boolean commit() {
        File newFile = new File(mFile.getAbsoluteFile() + EXT);
        if (newFile.exists() && !newFile.delete()) return false; // 删除
        FileOutputStream out = null;
        try {
            if (newFile.createNewFile()) return false;
            out = new FileOutputStream(newFile);
            byte[] bs = mInfo.createHead();
            out.write(bs);
            final FileOutputStream outFinal = out;
            if (mReader instanceof MetaDataReader) {
                mReader.each(new IDataReader.Consumer() {
                    @Override
                    public void accept(@NonNull WrapData wrapData) throws IOException {
                        if (writeData.containsKey(wrapData.meta)) return;
                        outFinal.write(wrapData.meta.write());
                        outFinal.write(wrapData.bytes);
                    }
                });

            } else if (mReader instanceof AllDataReader) {
                mReader.each(new IDataReader.Consumer() {
                    @Override
                    public void accept(@NonNull WrapData wrapData) throws IOException {
                        if (writeData.containsKey(wrapData.meta)) return;
                        byte[] bs = DataHolder.put(wrapData.meta, wrapData.data);
                        outFinal.write(wrapData.meta.write());
                        if (bs != null) outFinal.write(bs);
                    }
                });

            }
            if (mReader != null) throw new IllegalArgumentException("mReader type unknown.");
            writeMap(out);
            out.flush();
            return rename(newFile);
        } catch (IOException ex) {
            throw new IORuntimeException(ex);
        } finally {
            CloseUtils.closeIOQuietly(out);
        }
    }

    /**
     * 数据载入代理
     *
     * @param id    编号
     * @param value 值
     * @param type  值类型
     * @return 类本身
     */
    private Writer putProxy(short id, Object value, short type) {
        for (MetaData meta : writeData.keySet()) {
            if (meta.id == id) {
                if (meta.type != type)
                    throw new ClassCastException("the key is used, don't cast class.");
                writeData.remove(writeData);
                meta.type = type;
                writeData.put(meta, value);
                return this;
            }
        }
        writeData.put(new MetaData(), value);
        return this;
    }

    /**
     * 写入修改文件
     *
     * @param out 输出流
     * @throws IOException IOE
     */
    private void writeMap(OutputStream out) throws IOException {
        for (Map.Entry<MetaData, Object> set : writeData.entrySet()) {
            MetaData meta = set.getKey();
            byte[] bs = DataHolder.put(meta, set.getValue());
            out.write(meta.write());
            if (bs != null) out.write(bs);
        }
    }

    /**
     * 重命名新文件替换旧文件
     *
     * @param newFile 新文件
     * @return 是否操作成功
     */
    private boolean rename(File newFile) {
        return (!mFile.exists() || mFile.delete()) && newFile.renameTo(mFile);
    }
}
