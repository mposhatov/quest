package com.mposhatov.request;

import com.mposhatov.dto.ActiveGame;

public class GetUpdateActiveGameRequest extends Request<ActiveGame> {

    public GetUpdateActiveGameRequest(long clientId) {
        super(clientId);
    }
}
