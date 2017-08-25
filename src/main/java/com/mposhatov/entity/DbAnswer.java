package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "ANSWER")
public class DbAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DESCRIPTION", length = 300, nullable = false)
    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NEXT_STEP_ID", nullable = true)
    private DbStep nextStep;

    @Column(name = "WINNING", nullable = false)
    private boolean winning;

    @Column(name = "GIVING_GOLDEN_COINS", nullable = false)
    private long givingGoldenCoins;

    @Column(name = "GIVING_GOLDEN_DIAMONDS", nullable = false)
    private long givingDiamonds;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "REQUIREMENT_SUBJECTS_OF_ANSWERS", joinColumns = {
            @JoinColumn(name = "ANSWER_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "SUBJECT_ID", nullable = false)})
    private List<DbSubject> requiredSubjects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "GIVING_SUBJECTS_OF_ANSWERS", joinColumns = {
            @JoinColumn(name = "ANSWER_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "SUBJECT_ID", nullable = false)})
    private List<DbSubject> givingSubjects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "REQUIREMENT_EVENTS_OF_ANSWERS", joinColumns = {
            @JoinColumn(name = "ANSWER_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "EVENT_ID", nullable = false)})
    private List<DbEvent> requiredEvents = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "GIVING_EVENTS_OF_ANSWERS", joinColumns = {
            @JoinColumn(name = "ANSWER_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "EVENT_ID", nullable = false)})
    private List<DbEvent> givingEvents = new ArrayList<>();

    protected DbAnswer() {
    }

    public DbAnswer(String description, DbStep nextStep, long givingGoldenCoins, long givingDiamonds) {
        this.description = description;
        this.nextStep = nextStep;
        this.winning = false;
        this.givingGoldenCoins = givingGoldenCoins;
        this.givingDiamonds = givingDiamonds;
    }

    //Выйгрышный шаг
    public DbAnswer(String description, long givingGoldenCoins, long givingDiamonds) {
        this.description = description;
        this.winning = true;
        this.givingGoldenCoins = givingGoldenCoins;
        this.givingDiamonds = givingDiamonds;
    }

    public DbAnswer(String description, DbStep nextStep) {
        this.description = description;
        this.nextStep = nextStep;
    }

    public DbAnswer(String description, boolean winning) {
        this.description = description;
        this.winning = winning;
    }

    public DbAnswer addRequirementSubject(DbSubject subject) {
        this.requiredSubjects.add(subject);
        return this;
    }

    public DbAnswer addRequirementSubjects(Collection<DbSubject> subjects) {
        this.requiredSubjects.addAll(subjects);
        return this;
    }

    public DbAnswer addGivingSubject(DbSubject subject) {
        this.givingSubjects.add(subject);
        return this;
    }

    public DbAnswer addGivingSubjects(Collection<DbSubject> subjects) {
        this.givingSubjects.addAll(subjects);
        return this;
    }

    public DbAnswer addRequirementEvent(DbEvent event) {
        this.requiredEvents.add(event);
        return this;
    }

    public DbAnswer addRequirementEvents(Collection<DbEvent> events) {
        this.requiredEvents.addAll(events);
        return this;
    }

    public DbAnswer addGivingEvent(DbEvent event) {
        this.givingEvents.add(event);
        return this;
    }

    public DbAnswer addGivingEvents(Collection<DbEvent> events) {
        this.givingEvents.addAll(events);
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public DbStep getNextStep() {
        return nextStep;
    }

    public boolean isWinning() {
        return winning;
    }

    public long getGivingGoldenCoins() {
        return givingGoldenCoins;
    }

    public long getGivingDiamonds() {
        return givingDiamonds;
    }

    public List<DbSubject> getRequiredSubjects() {
        return requiredSubjects;
    }

    public List<DbSubject> getGivingSubjects() {
        return givingSubjects;
    }

    public List<DbEvent> getRequiredEvents() {
        return requiredEvents;
    }

    public List<DbEvent> getGivingEvents() {
        return givingEvents;
    }
}
