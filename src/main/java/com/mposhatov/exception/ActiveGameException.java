package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ActiveGameException {

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Active game does not exist")
    public static class DoesNotExist extends LogicException {

        private Long activeGameId;

        public DoesNotExist(Long activeGameId) {
            super(String.format("Active game (id = %d) does not exist.", activeGameId), null);
            this.activeGameId = activeGameId;
        }

        public Long getActiveGameId() {
            return activeGameId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Active game does not contain this warrior")
    public static class DoesNotContainedWarrior extends LogicException {

        private Long activeGameId;
        private Long warriorId;

        public DoesNotContainedWarrior(Long activeGameId, Long warriorId) {
            super(String.format("Active game (id = %d) does not contain warrior (id =  %d)",
                    activeGameId, warriorId), null);
            this.activeGameId = activeGameId;
            this.warriorId = warriorId;
        }

        public Long getActiveGameId() {
            return activeGameId;
        }

        public Long getWarriorId() {
            return warriorId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Active game does not contain two clients")
    public static class DoesNotContainTwoClients extends LogicException {

        private Long activeGameId;

        public DoesNotContainTwoClients(Long activeGameId) {
            super(String.format("Active game (id = %d) does not contain two clients", activeGameId), null);
            this.activeGameId = activeGameId;
        }

        public Long getActiveGameId() {
            return activeGameId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Active game does not contain win client")
    public static class DoesNotContainWinClient extends LogicException {

        private Long activeGameId;

        public DoesNotContainWinClient(Long activeGameId) {
            super(String.format("Active game (id = %d) does not contain win client", activeGameId), null);
            this.activeGameId = activeGameId;
        }

        public Long getActiveGameId() {
            return activeGameId;
        }
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Invalid current step in the queue in the active game")
    public static class InvalidCurrentStepInQueue extends LogicException {

        private Long activeGameId;

        public InvalidCurrentStepInQueue(Long activeGameId) {
            super(String.format("Invalid current step in the queue in the active game (id = %d)", activeGameId), null);
            this.activeGameId = activeGameId;
        }

        public Long getActiveGameId() {
            return activeGameId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Game is still active")
    public static class Close extends LogicException {

        private Long activeGameId;

        public Close(Long activeGameId) {
            super(String.format("Game (id = %d) is still active", activeGameId), null);
            this.activeGameId = activeGameId;
        }

        public Long getActiveGameId() {
            return activeGameId;
        }
    }

}
