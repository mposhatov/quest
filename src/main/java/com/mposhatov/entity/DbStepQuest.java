package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "STEP_QUEST")
public class DbStepQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DESCRIPTION", length = 4000, nullable = false)
    private String description;

    @Column(name = "STEP", nullable = false)
    private long step;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QUEST_ID", nullable = false)
    private DbQuest dbQuest;

    @OneToMany(mappedBy = "stepQuest", fetch = FetchType.LAZY)
    private List<DbAnswerStep> answerSteps = new ArrayList<>();

    protected DbStepQuest() {
    }

    public DbStepQuest(String description, long step, DbQuest dbQuest) {
        this.description = description;
        this.step = step;
        this.dbQuest = dbQuest;
    }

    public void addAnswerStep(DbAnswerStep answerStep) {
        answerSteps.add(answerStep);
    }

    public void addAnswerSteps(Collection<DbAnswerStep> answerSteps) {
        answerSteps.addAll(answerSteps);
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public long getStep() {
        return step;
    }

    public DbQuest getDbQuest() {
        return dbQuest;
    }

    public List<DbAnswerStep> getAnswerSteps() {
        return answerSteps;
    }
}
