package me.limeice.common.rxcache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.limeice.common.function.CloseUtils;

class Utils {

    public static void inputSteamToOutputStream(InputStream in, OutputStream out) throws IOException {
        int size;
        byte[] bytes = new byte[1024];
        while ((size = in.read(bytes)) != -1) {
            out.write(bytes, 0, size);
        }
        CloseUtils.closeIOQuietly(out);
    }

    public static void inputSteamToFile(InputStream in, File file) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        int size;
        byte[] bytes = new byte[1024];
        while ((size = in.read(bytes)) != -1) {
            outputStream.write(bytes, 0, size);
        }
        CloseUtils.closeIOQuietly(outputStream);
    }
}
