package com.mposhatov.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LogicException extends Exception {

    private final Logger logger = LoggerFactory.getLogger(LogicException.class);

    public LogicException(String message, Throwable cause) {
        super(message, cause);
        logger.error(this.getMessage(), this);
    }

}
