package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class WarriorException {

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Warrior does not exist")
    public static class DoesNotExist extends LogicException {

        private Long warriorId;

        public DoesNotExist(Long warriorId) {
            super(String.format("Warrior (id = %d) does not exist", warriorId), null);
            this.warriorId = warriorId;
        }

        public Long getWarriorId() {
            return warriorId;
        }
    }
}
