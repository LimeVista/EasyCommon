package me.limeice.common.datahelper;

@SuppressWarnings("unused")
public class FileCorruptedException extends RuntimeException {

    public FileCorruptedException() {
        super("File Corrupted!");
    }

    public FileCorruptedException(String message) {
        super(message);
    }

    public FileCorruptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileCorruptedException(Throwable cause) {
        super(cause);
    }
}
