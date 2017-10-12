package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.FORBIDDEN,
        reason = "Blow to ally")
public class BlowToAllyException extends LogicException {

    private long attackWarriorId;
    private long defendWarriorId;

    public BlowToAllyException(long attackWarriorId, long defendWarriorId) {
        super(String.format("Warrior (id = %d) blow the ally warrior with id: %d.",
                attackWarriorId, defendWarriorId), null);
        this.attackWarriorId = attackWarriorId;
        this.defendWarriorId = defendWarriorId;
    }

    public long getAttackWarriorId() {
        return attackWarriorId;
    }

    public long getDefendWarriorId() {
        return defendWarriorId;
    }
}
