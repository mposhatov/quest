package com.mposhatov.request;

import com.mposhatov.dto.ActiveGame;

public class GetUpdateActiveGameRequest extends Request<ActiveGame> {

    private long activeGameId;

    public GetUpdateActiveGameRequest(long clientId, long activeGameId) {
        this.activeGameId = activeGameId;
    }

    public long getActiveGameId() {
        return activeGameId;
    }
}
