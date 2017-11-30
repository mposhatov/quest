package com.mposhatov.holder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActiveGameSearchRequest that = (ActiveGameSearchRequest) o;

        return client != null ? client.equals(that.client) : that.client == null;
    }

    @Override
    public int hashCode() {
        return client != null ? client.hashCode() : 0;
    }
}
