package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.UNPROCESSABLE_ENTITY,
        reason = "Client does not exist")
public class ClientDoesNotExistException extends LogicException {

    private long clientId;

    public ClientDoesNotExistException(long clientId) {
        super(String.format("Client (id = %d) does not exist", clientId), null);
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
    }
}
