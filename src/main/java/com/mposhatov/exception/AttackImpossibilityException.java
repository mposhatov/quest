package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Attack impossibility")
public class AttackImpossibilityException extends LogicException {

    private Long attackWarriorId;
    private Long defendWarriorId;

    public AttackImpossibilityException(Long attackWarriorId, Long defendWarriorId) {
        super(String.format("This warrior (id = %d) can not attack warrior (id = %d)", attackWarriorId, defendWarriorId), null);
        this.attackWarriorId = attackWarriorId;
        this.defendWarriorId = defendWarriorId;
    }

    public Long getAttackWarriorId() {
        return attackWarriorId;
    }

    public Long getDefendWarriorId() {
        return defendWarriorId;
    }
}
