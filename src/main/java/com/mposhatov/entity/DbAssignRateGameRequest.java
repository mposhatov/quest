package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "ASSIGN_RATE_GAME_REQUEST")
public class DbAssignRateGameRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    protected DbAssignRateGameRequest() {
    }

    public DbAssignRateGameRequest(DbClient client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public DbClient getClient() {
        return client;
    }
}
