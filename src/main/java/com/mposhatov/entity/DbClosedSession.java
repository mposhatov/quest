package com.mposhatov.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CLOSED_SESSION")
public class DbClosedSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FINISHED_AT", nullable = false)
    private Date finishedAt;

    @Column(name = "IP", nullable = false)
    private String ip;

    @Column(name = "USER_AGENT", nullable = false)
    private String userAgent;

    public DbClosedSession(DbClient client, Date createdAt, String ip, String userAgent) {
        this.client = client;
        this.createdAt = createdAt;
        this.finishedAt = new Date();
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
