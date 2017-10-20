package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Active game does not contain two clients")
public class ActiveGameDoesNotContainTwoClientsException extends LogicException {

    private long activeGameId;

    public ActiveGameDoesNotContainTwoClientsException(long activeGameId) {
        super(String.format("Active game (id = %d) does not contain two clients", activeGameId), null);
        this.activeGameId = activeGameId;
    }

    public long getActiveGameId() {
        return activeGameId;
    }
}
