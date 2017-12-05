package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GetUpdateDActiveGameRequestException {

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Request to get update active game does not exist for this client")
    public static class DoesNotExist extends LogicException {

        private Long clientId;

        public DoesNotExist(Long clientId) {
            super(String.format("Request to get update active game does not exist for client (id = %d)", clientId), null);
            this.clientId = clientId;
        }

        public Long getClientId() {
            return clientId;
        }
    }
}
