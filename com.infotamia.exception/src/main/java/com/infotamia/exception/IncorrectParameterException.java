package com.infotamia.exception;

/**
 * @author Mohammed Al-Ani
 */
public class IncorrectParameterException extends BaseException {
    private static final long serialVersionUID = 3471734080290429947L;

    public IncorrectParameterException(String message, BaseErrorCode baseErrorCode) {
        super(message, baseErrorCode);
    }
}
