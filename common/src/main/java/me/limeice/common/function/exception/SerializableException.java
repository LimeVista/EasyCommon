package me.limeice.common.function.exception;

public class SerializableException extends Exception {

    public SerializableException() {
        super("Deep cloning failed! This class must inherit the Serializable interface");
    }

    public SerializableException(String msg) {
        super("Deep cloning failed! This class must inherit the Serializable interface" + msg);
    }
}
