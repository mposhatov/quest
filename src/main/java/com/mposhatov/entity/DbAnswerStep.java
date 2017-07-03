package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "ANSWER_STEP")
public class DbAnswerStep {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DESCRIPTION", length = 300, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STEP_QUEST_ID", nullable = false)
    private DbStepQuest stepQuest;

    public DbAnswerStep() {
    }

    public DbAnswerStep(String description, DbStepQuest stepQuest) {
        this.description = description;
        this.stepQuest = stepQuest;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public DbStepQuest getStepQuest() {
        return stepQuest;
    }
}
