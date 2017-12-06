package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class DatabaseContentException {

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Incorrect target")
    public static class IncorrectTarget extends LogicException {

        public IncorrectTarget() {
            super("Incorrect target", null);
        }

    }
}
