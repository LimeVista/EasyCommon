package me.limeice.common.function.helper;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import me.limeice.common.function.CloseUtils;

/**
 * 写入源
 */
public class WriterSource {

    /**
     * 写入目标文件
     */
    private final File file;

    /**
     * 写入目标流
     */
    private OutputStream outStream;

    /**
     * 创建写入源
     *
     * @param file 写入目标文件
     */
    public WriterSource(@NonNull final File file) {
        this.file = file;
    }

    /**
     * 获得文件
     *
     * @return 文件
     */
    @NonNull
    public File getFile() {
        return file;
    }

    /**
     * 获得输出流，不建议自己创建流，该托管流能够便于处理很多问题
     *
     * @return 输出流
     */
    @NonNull
    public synchronized FileOutputStream getOutStream() {
        if (outStream == null)
            try {
                outStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        return (FileOutputStream) outStream;
    }

    /**
     * 获得写入者（不建议自己创建写入者）
     *
     * @return Writer
     */
    @NonNull
    public Writer getWriter() {
        return new OutputStreamWriter(getOutStream());
    }

    /**
     * 托管释放
     */
    public void close() {
        CloseUtils.closeIOQuietly(outStream);
    }
}
