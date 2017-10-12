package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.UNPROCESSABLE_ENTITY,
        reason = "Warrior does not exist")
public class WarriorDoesNotExistException extends LogicException {

    private long warriorId;

    public WarriorDoesNotExistException(long warriorId) {
        super(String.format("Warrior (id = %d) does not exist", warriorId), null);
        this.warriorId = warriorId;
    }

    public long getWarriorId() {
        return warriorId;
    }
}
