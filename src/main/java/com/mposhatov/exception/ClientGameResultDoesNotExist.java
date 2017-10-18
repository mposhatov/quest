package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.FORBIDDEN,
        reason = "Client game result does not exist for this client")
public class ClientGameResultDoesNotExist extends LogicException {

    private long clientId;

    public ClientGameResultDoesNotExist(long clientId) {
        super(String.format("Client game result does not exist for client (id = %d)", clientId), null);
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
    }
}
