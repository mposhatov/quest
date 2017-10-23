package com.mposhatov.request;

import com.mposhatov.dto.StepActiveGame;

public class GetUpdatedActiveGameRequest extends Request<StepActiveGame> {

    public GetUpdatedActiveGameRequest(long clientId) {
        super(clientId);
    }
}
