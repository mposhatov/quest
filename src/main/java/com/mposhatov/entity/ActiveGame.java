package com.mposhatov.entity;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class ActiveGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "QUEST_ID", nullable = false)
    private DbQuest quest;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "STEP_ID", nullable = false)
    private DbStep step;

    @Column(name = "CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    protected ActiveGame() {
    }

    protected ActiveGame(DbQuest quest, DbStep step) {
        this.quest = quest;
        this.step = step;
        this.createdAt = new Date();
    }

    public ActiveGame setStep(DbStep step) {
        this.step = step;
        return this;
    }

    public Long getId() {
        return id;
    }

    public DbStep getStep() {
        return step;
    }

    public DbQuest getQuest() {
        return quest;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

}
