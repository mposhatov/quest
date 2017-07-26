package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "ACTIVE_GAME")
public class DbActiveGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CLIENT_ID")
    private long clientId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "QUEST_ID", nullable = false)
    private DbQuest quest;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "STEP_ID", nullable = false)
    private DbStep step;

    @Column(name = "CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "ANONYMOUS", nullable = false)
    private boolean anonymous;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "SUBJECTS_OF_ACTIVE_GAME",
            joinColumns = {@JoinColumn(name = "ACTIVE_GAME_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SUBJECT_ID", nullable = false)})
    private List<DbSubject> subjects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "EVENTS_OF_ACTIVE_GAME",
            joinColumns = {@JoinColumn(name = "ACTIVE_GAME_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "EVENT_ID", nullable = false)})
    private List<DbEvent> completedEvents = new ArrayList<>();


    protected DbActiveGame() {
    }

    public DbActiveGame(Long clientId, DbQuest quest, DbStep step, boolean anonymous) {
        this.clientId = clientId;
        this.quest = quest;
        this.step = step;
        this.anonymous = anonymous;
        this.createdAt = new Date();
    }

    public DbActiveGame setStep(DbStep step) {
        this.step = step;
        return this;
    }

    public DbActiveGame addSubject(DbSubject subject) {
        this.subjects.add(subject);
        return this;
    }

    public DbActiveGame addSubjects(Collection<DbSubject> subjects) {
        this.subjects.addAll(subjects);
        return this;
    }

    public DbActiveGame addEvent(DbEvent event) {
        this.completedEvents.add(event);
        return this;
    }

    public DbActiveGame addEvents(Collection<DbEvent> events) {
        this.completedEvents.addAll(events);
        return this;
    }

    public List<DbAnswer> getAvailableAnswers() {
        final List<DbAnswer> allAnswers = getStep().getAnswers();
        return allAnswers.stream()
                .filter(o -> getSubjects().containsAll(o.getRequirementSubjects()))
                .filter(o -> getCompletedEvents().containsAll(o.getRequirementEvents()))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public DbStep getStep() {
        return step;
    }

    public DbQuest getQuest() {
        return quest;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Long getClientId() {
        return clientId;
    }

    public List<DbSubject> getSubjects() {
        return subjects;
    }

    public List<DbEvent> getCompletedEvents() {
        return completedEvents;
    }

    public boolean isAnonymous() {
        return anonymous;
    }
}
