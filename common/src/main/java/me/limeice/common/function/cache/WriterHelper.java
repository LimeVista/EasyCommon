package me.limeice.common.function.cache;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import me.limeice.common.function.CloseUtils;

public class WriterHelper {

    private final File file;

    private OutputStream outStream;

    WriterHelper(@NonNull final File file) {
        this.file = file;
    }

    @NonNull
    public File getFile() {
        return file;
    }

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

    @NonNull
    public Writer getWriter() {
        return new OutputStreamWriter(getOutStream());
    }

    public void close() {
        CloseUtils.closeIOQuietly(outStream);
    }
}
