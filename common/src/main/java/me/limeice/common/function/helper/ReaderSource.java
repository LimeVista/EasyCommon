package me.limeice.common.function.helper;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import me.limeice.common.function.CloseUtils;

public class ReaderSource {

    /**
     * 读取目标文件
     */
    private final File file;

    /**
     * 读取目标流
     */
    private InputStream inputStream;

    /**
     * 创建读取源
     *
     * @param file 读取目标文件
     */
    public ReaderSource(@NonNull final File file) {
        this.file = file;
    }

    /**
     * 获得目标文件
     *
     * @return 文件
     */
    @NonNull
    public File getFile() {
        return file;
    }

    /**
     * 获得读取流，不建议自己创建，该托管方式能够便于处理很多问题
     *
     * @return 读取流
     */
    @NonNull
    public synchronized FileInputStream getInputStream() {
        if (inputStream == null)
            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        return (FileInputStream) inputStream;
    }

    /**
     * 获得读取者，不建议自己创建，该托管方式能够便于处理很多问题
     *
     * @return 读取流
     */
    @NonNull
    public Reader getReader() {
        return new InputStreamReader(getInputStream());
    }

    /**
     * 托管释放
     */
    public void close() {
        CloseUtils.closeIOQuietly(inputStream);
    }

}
