package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Hierarchy warrior does not available")
public class HierarchyWarriorDoesNotAvailableException extends LogicException {

    private Long hierarchyWarriorId;

    public HierarchyWarriorDoesNotAvailableException(Long hierarchyWarriorId) {
        super(String.format("Hierarchy warrior (id = %d) does not available", hierarchyWarriorId), null);
        this.hierarchyWarriorId = hierarchyWarriorId;
    }

    public Long getHierarchyWarriorId() {
        return hierarchyWarriorId;
    }
}
