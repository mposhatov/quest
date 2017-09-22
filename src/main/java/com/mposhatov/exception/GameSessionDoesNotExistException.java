package com.mposhatov.exception;

public class GameSessionDoesNotExistException extends LogicException {

    public GameSessionDoesNotExistException(long gameSessionId) {
        super(String.format("Game with id: %d does not exist.", gameSessionId), null);
    }

}
