package me.limeice.common.datahelper;

public class NotFoundDataException extends RuntimeException {

    public NotFoundDataException(short id) {
        super("Not exist key: " + id);
    }
}
