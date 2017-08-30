package com.mposhatov;

public class LogicException extends Exception{

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class SimpleGameIsNotReached extends LogicException  {
        public SimpleGameIsNotReached(long clientId, long simpleGameId) {
            super(String.format("Gamer with id %d do not reached simple game %d", clientId, simpleGameId), null);
        }
    }

}
