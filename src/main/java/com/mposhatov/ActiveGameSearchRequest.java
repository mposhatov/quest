package com.mposhatov;

import com.mposhatov.dto.Client;

import java.util.Date;

public class ActiveGameSearchRequest {
    private Client client;
    private Date createdAt;

    public ActiveGameSearchRequest(Client client) {
        this.client = client;
        this.createdAt = new Date();
    }

    public Client getClient() {
        return client;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
