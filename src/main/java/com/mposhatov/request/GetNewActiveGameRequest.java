package com.mposhatov.request;

import com.mposhatov.dto.ActiveGame;

public class GetNewActiveGameRequest extends Request<ActiveGame> {

    public GetNewActiveGameRequest(long clientId) {
        super(clientId);
    }

}
