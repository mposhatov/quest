package com.mposhatov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Expected another client")
public class ExpectedAnotherClientException extends LogicException {

    private Long clientId;

    public ExpectedAnotherClientException(Long clientId) {
        super(String.format("Expected another client. Current client (id = %d)", clientId), null);
        this.clientId = clientId;
    }

    public Long getClientId() {
        return clientId;
    }
}
