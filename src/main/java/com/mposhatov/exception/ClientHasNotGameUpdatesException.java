package com.mposhatov.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Client has not game updates")
public class ClientHasNotGameUpdatesException extends LogicException {

    private long clientId;

    public ClientHasNotGameUpdatesException(long clientId) {
        super(String.format("Client (id = %d) has not game updates", clientId), null);
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
    }
}
