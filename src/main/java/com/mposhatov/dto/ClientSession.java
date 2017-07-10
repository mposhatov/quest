package com.mposhatov.dto;

import com.mposhatov.entity.ClientStatus;

import java.util.Date;

public class ClientSession {

    private long id;
    private Client client;
    private ClientStatus clientStatus;
    private Date createdAt;
    private Date finishedAt;
    private String ip;
    private String userAgent;

    public ClientSession(long id, Client client, ClientStatus clientStatus, Date createdAt, String ip, String userAgent) {
        this.id = id;
        this.client = client;
        this.clientStatus = clientStatus;
        this.createdAt = createdAt;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public ClientSession(long id, Client client, ClientStatus clientStatus, Date createdAt, Date finishedAt, String ip, String userAgent) {
        this.id = id;
        this.client = client;
        this.clientStatus = clientStatus;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public ClientStatus getClientStatus() {
        return clientStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
