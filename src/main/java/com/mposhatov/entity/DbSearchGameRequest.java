package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "SEARCH_GAME_REQUEST")
public class DbSearchGameRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    protected DbSearchGameRequest() {
    }

    public DbSearchGameRequest(DbClient client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public DbClient getClient() {
        return client;
    }
}
