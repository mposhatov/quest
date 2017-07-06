package com.mposhatov.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CLOSED_GAME")
public class DbClosedGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "QUEST_ID", nullable = false)
    private DbQuest quest;

    @Column(name = "STARTED_AT", nullable = false)
    private Date startedAt;

    @Column(name = "FINISHED_AT", nullable = false)
    private Date finishedAt;

    @Column(name = "GAME_COMPLETED", nullable = false)
    private boolean gameCompleted;

    protected DbClosedGame() {
    }

    public DbClosedGame(DbClient client, DbQuest quest, Date startedAt, Date finishedAt, boolean gameCompleted) {
        this.client = client;
        this.quest = quest;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.gameCompleted = gameCompleted;
    }

    public Long getId() {
        return id;
    }

    public DbClient getClient() {
        return client;
    }

    public DbQuest getQuest() {
        return quest;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public boolean isGameCompleted() {
        return gameCompleted;
    }
}
