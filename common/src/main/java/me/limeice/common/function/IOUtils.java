package me.limeice.common.function;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * IO处理工具
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2018/03/15
 *     desc  : IO 工具类
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
public final class IOUtils {

    private IOUtils() {
        throw new UnsupportedOperationException("Don't instantiate...");
    }

    /**
     * 缓冲区大小
     */
    private static final int BUFFER_SIZE = 4096;

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
    public static void checkFileIfNotExistCreate(File file) throws IOException {
        if (!file.exists()) {
            if (!file.createNewFile())
                throw new IOException("Failure to create a file!File Path->" + file.getAbsolutePath());
        }
    }

    /**
     * 压缩文件
     *
     * @param input  输入流（源文件）
     * @param output 输出流（压缩文件）
     * @throws IOException IOE
     */
    public static void zip(@NonNull InputStream input, @NonNull OutputStream output) throws IOException {
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(output);
            byte[] buf = new byte[BUFFER_SIZE];
            int len;
            while ((len = input.read(buf)) != -1) {
                gzip.write(buf, 0, len);
                gzip.flush();
            }
        } finally {
            CloseUtils.closeIOQuietly(input, gzip);
        }
    }

    /**
     * 解压文件
     *
     * @param input  输入流(压缩文件)
     * @param output 输出流（源文件）
     * @throws IOException IOE
     */
    public static void unzip(@NonNull InputStream input, @NonNull OutputStream output) throws IOException {
        GZIPInputStream gzip = null;
        try {
            gzip = new GZIPInputStream(input);
            byte[] buf = new byte[BUFFER_SIZE];
            int len;
            while ((len = gzip.read(buf)) != -1) {
                output.write(buf, 0, len);
            }
            output.flush();
        } finally {
            CloseUtils.closeIOQuietly(gzip, output);
        }
    }

    /**
     * 文件复制
     *
     * @param input  输入文件（被复制文件）
     * @param output 输出文件（复制文件）
     * @return 是否复制成功
     */
    public static boolean copyFile(@Nullable File input, @Nullable File output) {
        if (input == null || output == null)
            return false;
        FileChannel fileIn = null;
        FileChannel fileOut = null;
        try {
            fileIn = new FileInputStream(input).getChannel();
            fileOut = new FileOutputStream(output).getChannel();
            fileIn.transferTo(0, fileIn.size(), fileOut);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIOQuietly(fileIn, fileOut);
        }
    }

    /**
     * 移动文件
     *
     * @param input  输入文件
     * @param output 输出文件
     * @return 是否移动成功{@code true}成功否则失败
     */
    public static boolean moveFile(@Nullable File input, @Nullable File output) {
        return !(input == null || output == null) && input.exists() && input.renameTo(output);
    }

    /**
     * 复制源 InputStream 到目标 OutputStream
     *
     * @param source 源
     * @param sink   目标
     * @return 复制长度
     * @throws IOException IO 异常
     */
    public static long copy(InputStream source, OutputStream sink)
            throws IOException {
        long nRead = 0L;
        byte[] buf = new byte[BUFFER_SIZE];
        int n;
        while ((n = source.read(buf)) > 0) {
            sink.write(buf, 0, n);
            nRead += n;
        }
        return nRead;
    }

    /**
     * 复制 源 InputStream 到文件
     *
     * @param input   源
     * @param outFile 目标
     * @return 复制长度
     * @throws IOException IO 异常
     */
    public static long copy(InputStream input, File outFile) throws IOException {
        FileOutputStream output = new FileOutputStream(outFile);
        try {
            return copy(input, output);
        } finally {
            CloseUtils.closeIOQuietly(output);
        }
    }

    /**
     * 文件复制
     *
     * @param source 源文件
     * @param sink   目标文件
     * @return 复制长度
     * @throws IOException IO 异常
     */
    public static long copy(File source, File sink) throws IOException {
        FileChannel fileIn = null;
        FileChannel fileOut = null;
        try {
            fileIn = new FileInputStream(source).getChannel();
            fileOut = new FileOutputStream(sink).getChannel();
            return fileIn.transferTo(0, fileIn.size(), fileOut);
        } finally {
            CloseUtils.closeIOQuietly(fileIn, fileOut);
        }
    }

    /**
     * 删除文件夹
     *
     * @param directory 文件夹
     * @throws IOException IOE
     */
    public static void deleteDirectory(@NonNull final File directory) throws IOException {
        Objects.requireNonNull(directory, "directory is null");
        if (!directory.exists())
            return;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            if (!Files.isSymbolicLink(directory.toPath())) {
                cleanDirectory(directory);
            }
        } else {
            cleanDirectory(directory);
        }
        if (!directory.delete())
            throw new IOException("Unable to delete directory " + directory + ".");
    }

    /**
     * 删除文件夹
     *
     * @param directory 文件夹
     */
    public static void deleteDirectoryQuietly(@NonNull final File directory) {
        Objects.requireNonNull(directory, "directory is null");
        if (!directory.exists() || directory.isFile())
            return;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            if (!Files.isSymbolicLink(directory.toPath())) {
                cleanDirectoryQuietly(directory);
            }
            //noinspection ResultOfMethodCallIgnored
            directory.delete();
        } else {
            cleanDirectoryQuietly(directory);
        }
    }

    /**
     * 强制删除
     *
     * @param file 文件
     * @throws IOException IOE
     */
    private static void forceDelete(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            if (file.exists())
                if (!file.delete()) throw new IOException("Unable to delete file: " + file);
        }
    }

    /**
     * 清空文件夹
     *
     * @param directory 文件夹
     * @throws IOException IOE
     */
    private static void cleanDirectory(final File directory) throws IOException {
        final File[] files = directory.listFiles();
        if (files == null)
            throw new IOException("Failed to list contents of " + directory);
        IOException exception = null;
        for (final File file : files) {
            try {
                forceDelete(file);
            } catch (final IOException ioe) {
                exception = ioe;
            }
        }
        if (null != exception) throw exception;
    }

    /**
     * 清空文件夹
     *
     * @param directory 文件夹
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void cleanDirectoryQuietly(final File directory) {
        final File[] files = directory.listFiles();
        if (files != null)
            for (final File file : files) {
                if (file.isDirectory())
                    cleanDirectoryQuietly(file);
                else
                    file.delete();
            }
        directory.delete();
    }
}