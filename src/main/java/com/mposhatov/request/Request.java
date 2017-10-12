package com.mposhatov.request;

import org.springframework.web.context.request.async.DeferredResult;

public abstract class Request<CFG> {

    private DeferredResult<CFG> deferredResult = new DeferredResult<>();

    public void setResult(CFG cfg) {
        this.deferredResult.setResult(cfg);
    }

    public void setNoContent() {
        deferredResult.setResult(null);
    }

    public DeferredResult<CFG> getDeferredResult() {
        return deferredResult;
    }
}
