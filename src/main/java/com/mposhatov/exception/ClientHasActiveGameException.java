package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.FORBIDDEN,
        reason = "Client already has active game")
public class ClientHasActiveGameException extends LogicException {

    private long clientId;

    public ClientHasActiveGameException(long clientId) {
        super(String.format("Client (id = %d) already has active game"), null);
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
    }
}
