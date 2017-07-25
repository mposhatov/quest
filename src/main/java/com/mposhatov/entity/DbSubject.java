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

    @Column(name = "VALUE", length = 20, nullable = true)
    private String value;

    @Column(name = "DESCRIPTION", length = 100, nullable = true)
    private String description;

    @Column(name = "NUMBER", nullable = false)
    private long number;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "REQUIREMENT_SUBJECTS_OF_ANSWERS",
            joinColumns = {@JoinColumn(name = "SUBJECT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "ANSWER_ID", nullable = false)})
    private List<DbAnswer> requirementAnswers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GIVING_SUBJECTS_OF_ANSWERS",
            joinColumns = {@JoinColumn(name = "SUBJECT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "ANSWER_ID", nullable = false)})
    private List<DbAnswer> givingAnswers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SUBJECTS_OF_ACTIVE_GAME",
            joinColumns = {@JoinColumn(name = "SUBJECT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "ACTIVE_GAME_ID", nullable = false)})
    private List<DbClientActiveGame> activeGames = new ArrayList<>();

    protected DbSubject() {
    }

    public DbSubject(String name, String description) {
        this(name, null, description);
    }

    public DbSubject(String name, String value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
        this.number = 1;
    }

    public DbSubject addDublicate() {
        this.number++;
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

    public List<DbAnswer> getRequirementAnswers() {
        return requirementAnswers;
    }

    public List<DbAnswer> getGivingAnswers() {
        return givingAnswers;
    }

    public List<DbClientActiveGame> getActiveGames() {
        return activeGames;
    }
}
