package com.infotamia.exception;

/**
 * @author Mohammed Al-Ani
 **/
public class UnknownException extends BaseException {
    public UnknownException(String message, BaseErrorCode baseErrorCode) {
        super(message, baseErrorCode);
    }
}
