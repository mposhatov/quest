package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "")
public class ExpectedAnotherWarrior extends LogicException {

    private long currentWarriorId;
    private long expectedWarriorId;

    public ExpectedAnotherWarrior(long currentWarriorId, long expectedWarriorId) {
        super(String.format("Expected warrior (id = %d). " +
                "Current warrior (id = %d)", expectedWarriorId, currentWarriorId), null);
        this.currentWarriorId = currentWarriorId;
        this.expectedWarriorId = expectedWarriorId;
    }

    public long getCurrentWarriorId() {
        return currentWarriorId;
    }

    public long getExpectedWarriorId() {
        return expectedWarriorId;
    }
}
