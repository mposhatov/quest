package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "CLIENT_GAME_RESULT")
public class DbClientGameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
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

    public Long getId() {
        return id;
    }

    public DbClient getClient() {
        return client;
    }

    public DbClosedGame getClosedGame() {
        return closedGame;
    }

    public boolean isWin() {
        return win;
    }

    public long getRating() {
        return rating;
    }
}
