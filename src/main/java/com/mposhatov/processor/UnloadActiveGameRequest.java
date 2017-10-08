package com.mposhatov.processor;

import com.mposhatov.dto.ActiveGame;
import org.springframework.web.context.request.async.DeferredResult;

public class UnloadActiveGameRequest extends DeferredResult<ActiveGame> {

    private long clientId;
    private long activeGameId;

    public UnloadActiveGameRequest() {
    }

    public UnloadActiveGameRequest setClientId(long clientId) {
        this.clientId = clientId;
        return this;
    }

    public UnloadActiveGameRequest setActiveGameId(long activeGameId) {
        this.activeGameId = activeGameId;
        return this;
    }

    public long getActiveGameId() {
        return activeGameId;
    }

    public long getClientId() {
        return clientId;
    }
}
