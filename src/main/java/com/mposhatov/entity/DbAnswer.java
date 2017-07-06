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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "STEP_ID", nullable = false)
    private DbStep step;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "NEXT_STEP_ID", nullable = true)
    private DbStep nextStep;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "REQUIREMENT_SUBJECTS_OF_ANSWERS", joinColumns = {
            @JoinColumn(name = "ANSWER_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "SUBJECT_ID", nullable = false)})
    private List<DbSubject> requirementSubjects = new ArrayList<>();

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
    private List<DbEvent> requirementEvents = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "GIVING_EVENTS_OF_ANSWERS", joinColumns = {
            @JoinColumn(name = "ANSWER_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "EVENT_ID", nullable = false)})
    private List<DbEvent> givingEvents = new ArrayList<>();

    protected DbAnswer() {
    }

    public DbAnswer(String description, DbStep step, DbStep nextStep) {
        this.description = description;
        this.step = step;
        this.nextStep = nextStep;
    }

    public DbAnswer addRequirementSubject(DbSubject subject) {
        this.requirementSubjects.add(subject);
        return this;
    }

    public DbAnswer addRequirementSubjects(Collection<DbSubject> subjects) {
        this.requirementSubjects.addAll(subjects);
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
        this.requirementEvents.add(event);
        return this;
    }

    public DbAnswer addRequirementEvents(Collection<DbEvent> events) {
        this.requirementEvents.addAll(events);
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

    public DbStep getStep() {
        return step;
    }

    public List<DbSubject> getRequirementSubjects() {
        return requirementSubjects;
    }

    public List<DbSubject> getGivingSubjects() {
        return givingSubjects;
    }

    public List<DbEvent> getRequirementEvents() {
        return requirementEvents;
    }

    public List<DbEvent> getGivingEvents() {
        return givingEvents;
    }

    public DbStep getNextStep() {
        return nextStep;
    }
}
