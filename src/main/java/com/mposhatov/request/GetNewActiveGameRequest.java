package com.mposhatov.request;

import com.mposhatov.dto.ActiveGame;

public class GetNewActiveGameRequest extends Request<ActiveGame> {

    private long clientId;

    public GetNewActiveGameRequest(long clientId) {
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
    }
}
