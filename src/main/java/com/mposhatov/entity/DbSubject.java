package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SUBJECT")
public class DbSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "VALUE", length = 20, nullable = false)
    private String value;

    @Column(name = "DESCRIPTION", length = 100, nullable = false)
    private String description;

    @Column(name = "NUMBER", nullable = false)
    private long number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACTIVE_GAME_ID", nullable = true)
    private DbActiveGame activeGame;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "REQUIREMENT_SUBJECTS_OF_ANSWER", joinColumns = {
            @JoinColumn(name = "SUBJECT_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "ANSWER_ID", nullable = false)})
    private List<DbAnswer> requirementAnswers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GIVING_SUBJECTS_OF_ANSWER", joinColumns = {
            @JoinColumn(name = "SUBJECT_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "ANSWER_ID", nullable = false)})
    private List<DbAnswer> givingAnswers = new ArrayList<>();

    public DbSubject() {
    }

    public DbSubject(String name, String value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    public DbSubject setGame(DbActiveGame activeGame) {
        this.number++;
        this.activeGame = activeGame;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public long getNumber() {
        return number;
    }

    public DbActiveGame getActiveGame() {
        return activeGame;
    }

    public List<DbAnswer> getRequirementAnswers() {
        return requirementAnswers;
    }

    public List<DbAnswer> getGivingAnswers() {
        return givingAnswers;
    }
}
