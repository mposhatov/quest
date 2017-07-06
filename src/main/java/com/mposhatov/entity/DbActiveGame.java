package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ACTIVE_GAME")
public class DbActiveGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "QUEST_ID", nullable = false)
    private DbQuest quest;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "STEP_ID", nullable = false)
    private DbStep step;

    @Column(name = "CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

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

    public DbActiveGame(DbClient client, DbQuest quest, DbStep step) {
        this.client = client;
        this.quest = quest;
        this.step = step;
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

    public Long getId() {
        return id;
    }

    public DbClient getClient() {
        return client;
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

    public List<DbSubject> getSubjects() {
        return subjects;
    }

    public List<DbEvent> getCompletedEvents() {
        return completedEvents;
    }
}
