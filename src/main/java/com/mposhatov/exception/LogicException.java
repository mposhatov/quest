package com.mposhatov.exception;

public abstract class LogicException extends Exception {

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

}
