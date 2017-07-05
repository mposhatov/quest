package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "CLOSED_GAME")
public class DbClosedGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUEST_ID", nullable = false)
    private DbQuest quest;

    @Column(name = "TOTAL_TIME_SEC", nullable = false)
    private long totalTimeSec;

    protected DbClosedGame() {
    }

    public DbClosedGame(DbClient client, DbQuest quest, long totalTimeSec) {
        this.client = client;
        this.quest = quest;
        this.totalTimeSec = totalTimeSec;
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

    public long getTotalTimeSec() {
        return totalTimeSec;
    }
}
