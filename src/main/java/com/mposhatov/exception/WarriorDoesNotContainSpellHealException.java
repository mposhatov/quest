package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Warrior does not contain spell heal")
public class WarriorDoesNotContainSpellHealException extends LogicException {

    private Long warriorId;
    private Long spellHealId;

    public WarriorDoesNotContainSpellHealException(Long warriorId, Long spellHealId) {
        super(String.format("Warrior (id = %d) does not contain spell heal (id = %d)", warriorId, spellHealId), null);
        this.warriorId = warriorId;
        this.spellHealId = spellHealId;
    }

    public Long getWarriorId() {
        return warriorId;
    }

    public Long getSpellHealId() {
        return spellHealId;
    }
}
