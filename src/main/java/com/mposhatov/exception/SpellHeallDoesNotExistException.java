package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Spell heal does not exist")
public class SpellHeallDoesNotExistException extends LogicException {

    private Long spellHealId;

    public SpellHeallDoesNotExistException(Long spellHealId) {
        super(String.format("Spell heal (id = %d) does not exist", spellHealId), null);
        this.spellHealId = spellHealId;
    }

    public Long getSpellHealId() {
        return spellHealId;
    }
}
