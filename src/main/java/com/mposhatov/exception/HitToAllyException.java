package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.FORBIDDEN,
        reason = "Hit to ally")
public class HitToAllyException extends LogicException {

    private long attackWarriorId;
    private long defendWarriorId;

    public HitToAllyException(long attackWarriorId, long defendWarriorId) {
        super(String.format("Warrior (id = %d) hit to the ally warrior with id: %d.",
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
