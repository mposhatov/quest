package com.mposhatov.request;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.TimeUnit;

public abstract class Request<CFG> {

    private DeferredResult<CFG> deferredResult = new DeferredResult<>(TimeUnit.MINUTES.toMillis(30));

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
