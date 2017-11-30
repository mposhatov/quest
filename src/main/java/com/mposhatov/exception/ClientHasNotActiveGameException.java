package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.FORBIDDEN,
        reason = "Client has not active game")
public class ClientHasNotActiveGameException extends LogicException {

    private long clientId;

    public ClientHasNotActiveGameException(long clientId) {
        super(String.format("Client (id = %d) has not active game", clientId), null);
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
    }
}
