package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "STEP")
public class DbStep {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DESCRIPTION", length = 4000, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QUEST_ID", nullable = false)
    private DbQuest dbQuest;

    @OneToMany(mappedBy = "step", fetch = FetchType.LAZY)
    private List<DbAnswer> answerSteps = new ArrayList<>();

    protected DbStep() {
    }

    public DbStep(String description, DbQuest dbQuest) {
        this.description = description;
        this.dbQuest = dbQuest;
    }

    public void addAnswerStep(DbAnswer answerStep) {
        answerSteps.add(answerStep);
    }

    public void addAnswerSteps(Collection<DbAnswer> answerSteps) {
        answerSteps.addAll(answerSteps);
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public DbQuest getDbQuest() {
        return dbQuest;
    }

    public List<DbAnswer> getAnswerSteps() {
        return answerSteps;
    }
}
