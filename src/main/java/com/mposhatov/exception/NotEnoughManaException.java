package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Not enough mana")
public class NotEnoughManaException extends LogicException {

    private Integer expectedMana;
    private Integer foundMana;

    public NotEnoughManaException(Integer expectedMana, Integer foundMana) {
        super(String.format("Not enough mana. Expected mana: %d, found mana: %d", expectedMana, foundMana), null);
        this.expectedMana = expectedMana;
        this.foundMana = foundMana;
    }

    public Integer getExpectedMana() {
        return expectedMana;
    }

    public Integer getFoundMana() {
        return foundMana;
    }
}
