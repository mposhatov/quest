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

    @Column(name = "BACKGROUND_NAME", nullable = false)
    private String backgroundName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "ANSWERS_OF_STEPS",
            joinColumns = {@JoinColumn(name = "STEP_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "ANSWER_ID", nullable = false)})
    private List<DbAnswer> answers = new ArrayList<>();

    protected DbStep() {
    }

    public DbStep(String description, String backgroundName) {
        this.description = description;
        this.backgroundName = backgroundName;
    }

    public void addAnswer(DbAnswer answerStep) {
        this.answers.add(answerStep);
    }

    public void addAnswers(Collection<DbAnswer> answerSteps) {
        this.answers.addAll(answerSteps);
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<DbAnswer> getAnswers() {
        return answers;
    }

    public String getBackgroundName() {
        return backgroundName;
    }
}
