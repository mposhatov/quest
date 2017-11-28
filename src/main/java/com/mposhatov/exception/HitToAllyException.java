package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Hit to ally")
public class HitToAllyException extends LogicException {

    private Long attackWarriorId;
    private Long defendWarriorId;

    public HitToAllyException(Long attackWarriorId, Long defendWarriorId) {
        super(String.format("Warrior (id = %d) hit to the ally warrior (id = %d)", attackWarriorId, defendWarriorId), null);
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
