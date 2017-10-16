package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Request to get update active game does not exist for this client")
public class GetUpdateActiveGameRequestDoesNotExistException extends LogicException {

    private long clientId;

    public GetUpdateActiveGameRequestDoesNotExistException(long clientId) {
        super(String.format("Request to get new active game does not exist for client (id = %d)", clientId), null);
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
    }
}
