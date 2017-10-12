package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.FORBIDDEN,
        reason = "Not enough resources to buy warrior")
public class NotEnoughResourcesToBuyWarrior extends LogicException {

    private long warriorShopId;

    public NotEnoughResourcesToBuyWarrior(long warriorShopId) {
        super(String.format("Not enough resources to buy warrior (id = %d)", warriorShopId), null);
        this.warriorShopId = warriorShopId;
    }

    public long getWarriorShopId() {
        return warriorShopId;
    }
}
