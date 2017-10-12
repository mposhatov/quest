package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.FAILED_DEPENDENCY,
        reason = "Client already in the queue")
public class ClientInTheQueueException extends LogicException {

    private long clientId;

    public ClientInTheQueueException(long clientId) {
        super(String.format("Client (id = %d) already in the queue.", clientId), null);
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
    }
}
