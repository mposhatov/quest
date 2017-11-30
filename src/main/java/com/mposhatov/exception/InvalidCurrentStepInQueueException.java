package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.FORBIDDEN,
        reason = "Invalid current step in the queue in the active game")
public class InvalidCurrentStepInQueueException extends LogicException {

    private long activeGameId;

    public InvalidCurrentStepInQueueException(long activeGameId) {
        super(String.format("Invalid current step in the queue in the active game (id = %d)", activeGameId), null);
        this.activeGameId = activeGameId;
    }

    public long getActiveGameId() {
        return activeGameId;
    }
}
