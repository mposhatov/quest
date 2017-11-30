package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Game is still active")
public class CloseActiveGameException extends LogicException {

    private Long activeGameId;

    public CloseActiveGameException(Long activeGameId) {
        super(String.format("Game (id = %d) is still active", activeGameId), null);
        this.activeGameId = activeGameId;
    }

    public Long getActiveGameId() {
        return activeGameId;
    }
}
