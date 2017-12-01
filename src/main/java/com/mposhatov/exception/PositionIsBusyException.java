package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Position is busy")
public class PositionIsBusyException extends LogicException {

    private Integer position;

    public PositionIsBusyException(Integer position) {
        super(String.format("Position %d is busy", position), null);
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }
}
