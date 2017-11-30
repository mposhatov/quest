package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Active game does not contain win client")
public class ActiveGameDoesNotContainWinClientException extends LogicException {

    private long activeGameId;

    public ActiveGameDoesNotContainWinClientException(long activeGameId) {
        super(String.format("Active game (id = %d) does not contain win client", activeGameId), null);
        this.activeGameId = activeGameId;
    }

    public long getActiveGameId() {
        return activeGameId;
    }
}
