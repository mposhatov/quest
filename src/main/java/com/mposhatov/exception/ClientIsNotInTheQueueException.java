package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.FAILED_DEPENDENCY,
        reason = "Client is not in the queue. " +
                "Client remove request or client in processing search game")
public class ClientIsNotInTheQueueException extends LogicException {

    private long clientId;

    public ClientIsNotInTheQueueException(long clientId) {
        super(String.format("Client (id = %d) is not in the queue." +
                "Client remove request or client in processing search game", clientId), null);
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
    }
}
