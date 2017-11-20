package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Not enough level to work with this warrior")
public class NotEnoughLevelToHierarchyWarriorException extends LogicException {

    private Long hierarchyWarriorId;
    private Integer requirementLevel;

    public NotEnoughLevelToHierarchyWarriorException(Long hierarchyWarriorId, Integer requirementLevel) {
        super(String.format("Not enough level (requirement level = %d) to work with warrior (id = %d)", requirementLevel, hierarchyWarriorId), null);
        this.hierarchyWarriorId = hierarchyWarriorId;
    }

    public Long getHierarchyWarriorId() {
        return hierarchyWarriorId;
    }

    public Integer getRequirementLevel() {
        return requirementLevel;
    }
}
