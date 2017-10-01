package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Active game does not contain commands")
public class ActiveGameDoesNotContainCommandsException extends LogicException {

    private long activeGameId;

    public ActiveGameDoesNotContainCommandsException(long activeGameId) {
        super(String.format("Active game with id: %d does not contain command", activeGameId), null);
        this.activeGameId  =activeGameId;
    }

    public long getActiveGameId() {
        return activeGameId;
    }
}
