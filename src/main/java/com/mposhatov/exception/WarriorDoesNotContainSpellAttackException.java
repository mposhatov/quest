package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Warrior does not contain spell attack")
public class WarriorDoesNotContainSpellAttackException extends LogicException {

    private Long warriorId;
    private Long spellAttackId;

    public WarriorDoesNotContainSpellAttackException(Long warriorId, Long spellAttackId) {
        super(String.format("Warrior (id = %d) does not contain spell attack (id = %d)", warriorId, spellAttackId), null);
        this.warriorId = warriorId;
        this.spellAttackId = spellAttackId;
    }

    public Long getWarriorId() {
        return warriorId;
    }

    public Long getSpellAttackId() {
        return spellAttackId;
    }

}
