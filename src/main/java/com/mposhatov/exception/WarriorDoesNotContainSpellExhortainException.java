package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Warrior does not contain spell exhortation")
public class WarriorDoesNotContainSpellExhortainException extends LogicException {

    private Long warriorId;
    private Long spellExhortationId;

    public WarriorDoesNotContainSpellExhortainException(Long warriorId, Long spellExhortationId) {
        super(String.format("Warrior (id = %d) does not contain spell exhortation (id = %d)",
                warriorId, spellExhortationId), null);
        this.warriorId = warriorId;
        this.spellExhortationId = spellExhortationId;
    }

    public Long getWarriorId() {
        return warriorId;
    }

    public Long getSpellExhortationId() {
        return spellExhortationId;
    }
}
