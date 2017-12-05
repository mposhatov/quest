package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ClientException {

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Client does not exist")
    public static class DoesNotExist extends LogicException {

        private Long clientId;

        public DoesNotExist(Long clientId) {
            super(String.format("Client (id = %d) does not exist", clientId), null);
            this.clientId = clientId;
        }

        public Long getClientId() {
            return clientId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Client already has active game")
    public static class HasActiveGame extends LogicException {

        private Long clientId;

        public HasActiveGame(Long clientId) {
            super(String.format("Client (id = %d) already has active game", clientId), null);
            this.clientId = clientId;
        }

        public Long getClientId() {
            return clientId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Client has not active game")
    public static class HasNotActiveGame extends LogicException {

        private Long clientId;

        public HasNotActiveGame(Long clientId) {
            super(String.format("Client (id = %d) has not active game", clientId), null);
            this.clientId = clientId;
        }

        public Long getClientId() {
            return clientId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Client has not game updates")
    public static class HasNotGameUpdates extends LogicException {

        private Long clientId;

        public HasNotGameUpdates(Long clientId) {
            super(String.format("Client (id = %d) has not game updates", clientId), null);
            this.clientId = clientId;
        }

        public Long getClientId() {
            return clientId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Client already in the queue")
    public static class InTheQueue extends LogicException {

        private Long clientId;

        public InTheQueue(Long clientId) {
            super(String.format("Client (id = %d) already in the queue.", clientId), null);
            this.clientId = clientId;
        }

        public Long getClientId() {
            return clientId;
        }
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Client is not in the queue. " +
            "Client remove request or client in processing search game")
    public static class IsNotInTheQueue extends LogicException {

        private Long clientId;

        public IsNotInTheQueue(Long clientId) {
            super(String.format("Client (id = %d) is not in the queue." +
                    "Client remove request or client in processing search game", clientId), null);
            this.clientId = clientId;
        }

        public Long getClientId() {
            return clientId;
        }
    }

}
