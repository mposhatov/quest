package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.UNPROCESSABLE_ENTITY,
        reason = "Active game does not exist")
public class ActiveGameDoesNotExistException extends LogicException {

    private long activeGameId;

    public ActiveGameDoesNotExistException(long activeGameId) {
        super(String.format("Active game with id: %d does not exist.", activeGameId), null);
        this.activeGameId = activeGameId;
    }

    public long getActiveGameId() {
        return activeGameId;
    }
}
