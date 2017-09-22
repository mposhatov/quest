package com.mposhatov.exception;

public class ClientIsNotInTheQueueException extends LogicException {

    public ClientIsNotInTheQueueException(long clientId) {
        super(String.format("Client with id: %d is not in the queue.", clientId), null);
    }

}
