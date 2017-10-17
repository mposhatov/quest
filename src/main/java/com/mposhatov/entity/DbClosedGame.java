package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CLOSED_GAME")
public class DbClosedGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_TIME", nullable = false)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FINISH_TIME", nullable = false)
    private Date finishTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "closedGame")
    private List<DbClosedGameResult> gameResults = new ArrayList<>();

    public DbClosedGame(Date startTime) {
        this.startTime = startTime;
        this.finishTime = new Date();
    }

    public DbClosedGame addGameResult(DbClient client, boolean win, long rating) {
        this.gameResults.add(new DbClosedGameResult(this, client, win, rating));
        return this;
    }

    public Long getId() {
        return id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public List<DbClosedGameResult> getGameResults() {
        return gameResults;
    }
}
