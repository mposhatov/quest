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
    private List<DbClientGameResult> clientGameResults = new ArrayList<>();

    protected DbClosedGame() {
    }

    public DbClosedGame(Date startTime) {
        this.startTime = startTime;
        this.finishTime = new Date();
    }

    public DbClosedGame addGameResults(List<DbClientGameResult> clientGameResults) {
        for (DbClientGameResult clientGameResult : clientGameResults) {
            this.addGameResult(clientGameResult);
        }
        return this;
    }

    public DbClosedGame addGameResult(DbClientGameResult clientGameResult) {
        this.clientGameResults.add(clientGameResult);
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

    public List<DbClientGameResult> getClientGameResults() {
        return clientGameResults;
    }
}
