package me.limeice.common.datahelper;

public class IORuntimeException extends RuntimeException {
    public IORuntimeException(Throwable t) {
        super("IOException->", t);
    }
}
