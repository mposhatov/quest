package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.UNPROCESSABLE_ENTITY,
        reason = "Warrior shop does not exist")
public class WarriorShopDoesNotExistException extends LogicException {

    private long warriorShopId;

    public WarriorShopDoesNotExistException(long warriorShopId) {
        super(String.format("Warrior shop (id = %d) does not exist", warriorShopId), null);
        this.warriorShopId = warriorShopId;
    }

    public long getWarriorShopId() {
        return warriorShopId;
    }
}
