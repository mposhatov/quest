package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.UNPROCESSABLE_ENTITY,
        reason = "Active game does not contain this warrior")
public class ActiveGameDoesNotContainedWarriorException extends LogicException {

    private long activeGameId;
    private long warriorId;

    public ActiveGameDoesNotContainedWarriorException(long activeGameId, long warriorId) {
        super(String.format("Active game (id = %d) does not contain warrior with id: %d",
                activeGameId, warriorId), null);
        this.activeGameId = activeGameId;
        this.warriorId = warriorId;
    }

    public long getActiveGameId() {
        return activeGameId;
    }

    public long getWarriorId() {
        return warriorId;
    }
}
