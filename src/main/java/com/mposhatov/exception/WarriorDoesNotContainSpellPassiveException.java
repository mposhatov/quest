package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Warrior does not contain spell passive")
public class WarriorDoesNotContainSpellPassiveException extends LogicException {

    private Long warriorId;
    private Long spellPassiveId;

    public WarriorDoesNotContainSpellPassiveException(Long warriorId, Long spellPassiveId) {
        super(String.format("Warrior (id = %d) does not contain spell passive (id = %d)", warriorId, spellPassiveId), null);
        this.warriorId = warriorId;
        this.spellPassiveId = spellPassiveId;
    }

    public Long getWarriorId() {
        return warriorId;
    }

    public Long getSpellPassiveId() {
        return spellPassiveId;
    }
}
