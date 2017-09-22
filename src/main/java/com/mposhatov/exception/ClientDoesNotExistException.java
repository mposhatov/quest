package com.mposhatov.exception;

public class ClientDoesNotExistException extends LogicException {

    public ClientDoesNotExistException(long clientId) {
        super(String.format("Client with id: %d doesnt exist", clientId), null);
    }

}
