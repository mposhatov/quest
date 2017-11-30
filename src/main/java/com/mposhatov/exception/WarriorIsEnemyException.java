package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Warrior is enemy")
public class WarriorIsEnemyException extends LogicException {

    private Long warriorId;

    public WarriorIsEnemyException(Long warriorId) {
        super(String.format("Warrior (id = %d) is enemy", warriorId), null);
        this.warriorId = warriorId;
    }

    public Long getWarriorId() {
        return warriorId;
    }
}
