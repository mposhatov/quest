package com.mposhatov.dto;

import java.util.Date;

public class ActiveGameSearchRequest {
    private long clientId;
    private Date createdAt;

    public ActiveGameSearchRequest(long clientId) {
        this.clientId = clientId;
        this.createdAt = new Date();
    }

    public long getClientId() {
        return clientId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
