package com.mposhatov.request;

import org.springframework.web.context.request.async.DeferredResult;

public abstract class Request<CFG> {
    private long clientId;
    private DeferredResult<CFG> deferredResult;

    public Request(long clientId, DeferredResult<CFG> deferredResult) {
        this.clientId = clientId;
        this.deferredResult = deferredResult;
    }

    public void setResult(CFG cfg) {
        this.deferredResult.setResult(cfg);
    }

    public long getClientId() {
        return clientId;
    }

    public DeferredResult<CFG> getDeferredResult() {
        return deferredResult;
    }
}
