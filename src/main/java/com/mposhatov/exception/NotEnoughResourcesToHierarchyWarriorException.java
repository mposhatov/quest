package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Not enough resources to work with this warrior")
public class NotEnoughResourcesToHierarchyWarriorException extends LogicException {

    private Long hierarchyWarriorId;
    private Integer goldCoins;
    private Integer diamonds;

    public NotEnoughResourcesToHierarchyWarriorException(Long hierarchyWarriorId, Integer goldCoins, Integer diamonds) {
        super(String.format("Not enough resources (requirement gold coins = %d, diamonds = %d) to work with warrior (id = %d)",
                goldCoins, diamonds, hierarchyWarriorId), null);
        this.hierarchyWarriorId = hierarchyWarriorId;
    }

    public long getHierarchyWarriorId() {
        return hierarchyWarriorId;
    }

    public Integer getGoldCoins() {
        return goldCoins;
    }

    public Integer getDiamonds() {
        return diamonds;
    }
}
