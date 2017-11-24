package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Spell attack does not exist")
public class SpellAttackDoesNotExistException extends LogicException {

    private Long spellAttackId;

    public SpellAttackDoesNotExistException(Long spellAttackId) {
        super(String.format("Spell attack (id = %d) does not exist", spellAttackId), null);
        this.spellAttackId = spellAttackId;
    }

    public Long getSpellAttackId() {
        return spellAttackId;
    }
}
