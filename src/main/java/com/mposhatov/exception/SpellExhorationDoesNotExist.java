package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Spell exhortation does not exist")
public class SpellExhorationDoesNotExist extends LogicException {

    private Long spellExhortationId;

    public SpellExhorationDoesNotExist(Long spellExhortationId) {
        super(String.format("Spell exhortation (id = %d) does not exist", spellExhortationId), null);
        this.spellExhortationId = spellExhortationId;
    }

    public Long getSpellExhortationId() {
        return spellExhortationId;
    }
}
