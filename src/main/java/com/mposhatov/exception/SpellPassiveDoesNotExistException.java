package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Spell passive does not exist")
public class SpellPassiveDoesNotExistException extends LogicException {

    private Long spellPassiveId;

    public SpellPassiveDoesNotExistException(Long spellPassiveId) {
        super(String.format("Spell passive (id = %d) does not exist", spellPassiveId), null);
        this.spellPassiveId = spellPassiveId;
    }

    public Long getSpellPassiveId() {
        return spellPassiveId;
    }
}
