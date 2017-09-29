package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.FORBIDDEN,
        reason = "Invalid current step in the queue in the game")
public class InvalidCurrentStepInQueueException extends LogicException {

    private long activeGameId;
    private long currentStep;

    public InvalidCurrentStepInQueueException(long activeGameId, long currentStep) {
        super(String.format("Invalid current step: %d in the queue in the game with id: %d",
                currentStep, activeGameId), null);
        this.activeGameId = activeGameId;
        this.currentStep = currentStep;
    }

    public long getActiveGameId() {
        return activeGameId;
    }

    public long getCurrentStep() {
        return currentStep;
    }
}
