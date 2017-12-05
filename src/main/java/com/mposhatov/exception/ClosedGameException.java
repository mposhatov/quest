package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ClosedGameException {

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Client game result does not exist for this client")
    public class ClientGameResultDoesNotExist extends LogicException {

        private Long clientId;

        public ClientGameResultDoesNotExist(Long clientId) {
            super(String.format("Client game result does not exist for client (id = %d)", clientId), null);
            this.clientId = clientId;
        }

        public Long getClientId() {
            return clientId;
        }
    }
}
