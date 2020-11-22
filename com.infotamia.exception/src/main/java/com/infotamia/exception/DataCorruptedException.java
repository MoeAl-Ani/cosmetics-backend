package com.infotamia.exception;

/**
 * @author Mohammed Al-Ani
 */
public class DataCorruptedException extends BaseException {
    private static final long serialVersionUID = 4151211974816842074L;

    public DataCorruptedException(String message, BaseErrorCode baseErrorCode) {
        super(message, baseErrorCode);
    }
}
