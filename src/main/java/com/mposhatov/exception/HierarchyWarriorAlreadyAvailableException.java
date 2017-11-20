package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Hierarchy warrior already available")
public class HierarchyWarriorAlreadyAvailableException extends LogicException {

    private Long hierarchyWarriorId;

    public HierarchyWarriorAlreadyAvailableException(Long hierarchyWarriorId) {
        super(String.format("Hierarchy warrior (id = %d) already available", hierarchyWarriorId), null);
        this.hierarchyWarriorId = hierarchyWarriorId;
    }

    public Long getHierarchyWarriorId() {
        return hierarchyWarriorId;
    }
}
