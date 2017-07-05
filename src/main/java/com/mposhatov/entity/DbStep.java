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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUEST_ID", nullable = false)
    private DbQuest dbQuest;

    @OneToMany(mappedBy = "step", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DbAnswer> answers = new ArrayList<>();

    protected DbStep() {
    }

    public DbStep(String description) {
        this.description = description;
    }

    public DbStep(String description, DbQuest dbQuest) {
        this.description = description;
        this.dbQuest = dbQuest;
    }

    public void addAnswerStep(DbAnswer answerStep) {
        this.answers.add(answerStep);
    }

    public void addAnswerSteps(Collection<DbAnswer> answerSteps) {
        this.answers.addAll(answerSteps);
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

    public List<DbAnswer> getAnswers() {
        return answers;
    }
}
