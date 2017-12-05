package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class HierarchyWarriorException {

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Hierarchy warrior does not exist")
    public static class DoesNotExist extends LogicException {

        private Long hierarchyWarriorId;

        public DoesNotExist(Long hierarchyWarriorId) {
            super(String.format("Hierarchy warrior (id = %d) does not exist", hierarchyWarriorId), null);
            this.hierarchyWarriorId = hierarchyWarriorId;
        }

        public Long getHierarchyWarriorId() {
            return hierarchyWarriorId;
        }
    }
}
