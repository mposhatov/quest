package com.mposhatov.request;

import org.springframework.web.context.request.async.DeferredResult;

public class GetNewActiveGameRequest<CFG> extends Request {
    public GetNewActiveGameRequest(long clientId, DeferredResult<CFG> deferredResult) {
        super(clientId, deferredResult);
    }
}
