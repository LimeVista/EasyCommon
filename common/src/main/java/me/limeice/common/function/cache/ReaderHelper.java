package me.limeice.common.function.cache;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import me.limeice.common.function.CloseUtils;

public class ReaderHelper {

    private final File file;

    private InputStream inputStream;

    ReaderHelper(@NonNull final File file) {
        this.file = file;
    }

    @NonNull
    public File getFile() {
        return file;
    }

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

    @NonNull
    public Reader getReader() {
        return new InputStreamReader(getInputStream());
    }

    public void close() {
        CloseUtils.closeIOQuietly(inputStream);
    }

}
