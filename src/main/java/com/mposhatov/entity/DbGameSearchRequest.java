package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "GAME_SEARCH_REQUEST")
public class DbGameSearchRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    protected DbGameSearchRequest() {
    }

    public DbGameSearchRequest(DbClient client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public DbClient getClient() {
        return client;
    }
}
