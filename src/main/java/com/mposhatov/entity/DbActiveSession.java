package com.mposhatov.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ACTIVE_SESSION")
public class DbActiveSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    @Convert(converter = ClientStatusConverter.class)
    @Column(name = "CLIENT_STATUS", nullable = false)
    private ClientStatus clientStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    @Column(name = "IP", nullable = false)
    private String ip;

    @Column(name = "USER_AGENT", nullable = false)
    private String userAgent;

    protected DbActiveSession() {
    }

    public DbActiveSession(DbClient client, ClientStatus clientStatus, String ip, String userAgent) {
        this.client = client;
        this.clientStatus = clientStatus;
        this.createdAt = new Date();
        this.ip = ip;
        this.userAgent = userAgent;
    }


    public Long getId() {
        return id;
    }

    public DbClient getClient() {
        return client;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public ClientStatus getClientStatus() {
        return clientStatus;
    }
}
