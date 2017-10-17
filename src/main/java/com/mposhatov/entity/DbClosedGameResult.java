package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "CLOSED_GAME_RESULT")
public class DbClosedGameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLOSED_GAME_ID", nullable = false)
    private DbClosedGame closedGame;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    @Column(name = "WIN", nullable = false)
    private boolean win;

    @Column(name = "RATING", nullable = false)
    private long rating;

    public DbClosedGameResult(DbClosedGame closedGame, DbClient client, boolean win, long rating) {
        this.closedGame = closedGame;
        this.client = client;
        this.win = win;
        this.rating = rating;
    }

    public Long getId() {
        return id;
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
