package me.limeice.common.function;

import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 字节流处理工具
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2018/03/15
 *     desc  : IO 工具类
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class IOUtils {

    private IOUtils() {
        throw new UnsupportedOperationException("Don't instantiate...");
    }

    /**
     * 缓冲区大小
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * 从输入流中读取数据，并转换为Byte数组
     *
     * @param inStream 待操作的输入流
     * @return Byte数组形式的html文件
     * @throws IOException 各种异常，包括IOException
     */
    @NonNull
    public static byte[] read(@NonNull InputStream inStream) throws IOException {
        // 字节缓冲流
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            // 循环读取
            while ((len = inStream.read(buffer)) != -1)
                outStream.write(buffer, 0, len);
            return outStream.toByteArray();
        } finally {
            CloseUtils.closeIOQuietly(outStream);
        }
    }

    /**
     * 从输入流中读取数据，并转换为Byte数组
     *
     * @param file 文件
     * @return Byte数组形式的html文件
     * @throws IOException IOException
     */
    @NonNull
    public static byte[] read(@NonNull File file) throws IOException {
        FileInputStream in = null;
        FileChannel inChannel = null;
        try {
            in = new FileInputStream(file);
            inChannel = in.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) inChannel.size());
            inChannel.read(buffer);
            return buffer.array();
        } finally {
            CloseUtils.closeIOQuietly(in, inChannel);
        }
    }

    /**
     * 从输入流中读取数据，并转换为Byte数组
     *
     * @param filePath 文件路径
     * @return Byte数组形式的html文件
     * @throws IOException IOException
     */
    @NonNull
    public static byte[] read(@NonNull String filePath) throws IOException {
        return read(new File(filePath));
    }


    /**
     * 写入数据
     *
     * @param filePath 文件路径（如果存在覆盖，否则创建）
     * @param msg      文本（被写入数据）
     * @throws IOException IOException
     */
    public static void write(@NonNull String filePath, @NonNull String msg) throws IOException {
        write(new File(filePath), msg);
    }

    /**
     * 写入数据
     *
     * @param filePath 文件路径（如果存在覆盖，否则创建）
     * @param bytes    字节数据（被写入数据）
     * @throws IOException IOException
     */
    public static void write(@NonNull String filePath, @NonNull byte[] bytes) throws IOException {
        write(new File(filePath), bytes);
    }

    /**
     * 写入数据
     *
     * @param file 文件（如果存在覆盖，否则创建）
     * @param msg  文本（被写入数据）
     * @throws IOException IOException
     */
    public static void write(@NonNull File file, @NonNull String msg) throws IOException {
        checkFileIfNotExistCreate(file);
        FileWriter out = null;
        try {
            out = new FileWriter(file);
            out.write(msg);
            out.flush();
        } finally {
            CloseUtils.closeIOQuietly(out);
        }
    }

    /**
     * 写入数据
     *
     * @param file  文件（如果存在覆盖，否则创建）
     * @param bytes 字节数据（被写入数据）
     * @throws IOException IOException
     */
    public static void write(@NonNull File file, @NonNull byte[] bytes) throws IOException {
        checkFileIfNotExistCreate(file);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(bytes);
            out.flush();
        } finally {
            CloseUtils.closeIOQuietly(out);
        }
    }

    /**
     * 写入数据
     *
     * @param file  文件（如果存在追加，否则创建）
     * @param bytes 字节数据（被写入数据）
     * @throws IOException IOException
     */
    public static void writeAppend(@NonNull File file, @NonNull byte[] bytes) throws IOException {
        checkFileIfNotExistCreate(file);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(bytes);
            out.flush();
        } finally {
            CloseUtils.closeIOQuietly(out);
        }
    }

    /**
     * 写入数据
     *
     * @param file   文件（如果存在覆盖，否则创建）
     * @param bytes  字节数据（被写入数据）
     * @param offset 偏移量
     * @param len    写入长度
     * @throws IOException IOException
     */
    public static void write(@NonNull File file, @NonNull byte[] bytes, int offset, int len) throws IOException {
        checkFileIfNotExistCreate(file);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(bytes, offset, len);
            out.flush();
        } finally {
            CloseUtils.closeIOQuietly(out);
        }
    }

    /**
     * 检查文件是否存在，否则创建
     *
     * @param file 文件
     * @throws IOException IOException
     */
    private static void checkFileIfNotExistCreate(File file) throws IOException {
        if (!file.exists()) {
            if (!file.createNewFile())
                throw new IOException("Failure to create a file!File Path->" + file.getAbsolutePath());
        }
    }
}
