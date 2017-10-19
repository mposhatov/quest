package com.mposhatov.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT_GAME_RESULT")
public class DbClientGameResult {

    @Id
    @GeneratedValue(generator = "client")
    @GenericGenerator(name = "client", strategy = "foreign", parameters = {@org.hibernate.annotations.Parameter(name = "property", value = "client")})
    @Column(name = "CLIENT_ID")
    private Long clientId;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLOSED_GAME_ID", nullable = false)
    private DbClosedGame closedGame;

    @Column(name = "WIN", nullable = false)
    private boolean win;

    @Column(name = "RATING", nullable = false)
    private long rating;

    protected DbClientGameResult() {
    }

    public DbClientGameResult(DbClient client, DbClosedGame closedGame, boolean win, long rating) {
        this.closedGame = closedGame;
        this.client = client;
        this.win = win;
        this.rating = rating;
    }

    public DbClosedGame getClosedGame() {
        return closedGame;
    }

    public DbClient getClient() {
        return client;
    }

    public boolean isWin() {
        return win;
    }

    public long getRating() {
        return rating;
    }
}
