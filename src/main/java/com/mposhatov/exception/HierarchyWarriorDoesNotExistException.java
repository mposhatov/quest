package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.UNPROCESSABLE_ENTITY,
        reason = "Hierarchy warrior does not exist")
public class HierarchyWarriorDoesNotExistException extends LogicException {

    private long hierarchyWarriorId;

    public HierarchyWarriorDoesNotExistException(long hierarchyWarriorId) {
        super(String.format("Hierarchy warrior (id = %d) does not exist", hierarchyWarriorId), null);
        this.hierarchyWarriorId = hierarchyWarriorId;
    }

    public long getHierarchyWarriorId() {
        return hierarchyWarriorId;
    }
}
