package com.mposhatov.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
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

//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BACKGROUND_ID", nullable = false)
    private DbBackground background;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUEST_ID", nullable = true)//nullable = false
    private DbQuest quest;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "ANSWERS_OF_STEPS",
            joinColumns = {@JoinColumn(name = "STEP_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "ANSWER_ID", nullable = false)})
    private List<DbAnswer> answers = new ArrayList<>();

    protected DbStep() {
    }

    public DbStep(String description, DbBackground background, DbQuest quest) {
        this.description = description;
        this.background = background;
        this.quest = quest;
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

    public DbQuest getQuest() {
        return quest;
    }

    public List<DbAnswer> getAnswers() {
        return answers;
    }

    public DbBackground getBackground() {
        return background;
    }
}
