package com.mposhatov.request;

import org.springframework.web.context.request.async.DeferredResult;

public class GetUpdateActiveGameRequest extends Request {

    private long activeGameId;

    public GetUpdateActiveGameRequest(long clientId, DeferredResult deferredResult, long activeGameId) {
        super(clientId, deferredResult);
        this.activeGameId = activeGameId;
    }

    public long getActiveGameId() {
        return activeGameId;
    }
}
