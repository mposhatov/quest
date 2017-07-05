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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUEST_ID", nullable = false)
    private DbQuest quest;

    @Column(name = "CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "SUBJECTS_OF_ACTIVE_GAME",
            joinColumns = {@JoinColumn(name = "ACTIVE_GAME_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SUBJECT_ID", nullable = false)})
    private List<DbSubject> subjects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "EVENTS_OF_ACTIVE_GAME",
            joinColumns = {@JoinColumn(name = "ACTIVE_GAME_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "EVENT_ID", nullable = false)})
    private List<DbEvent> completedEvents = new ArrayList<>();

    protected DbActiveGame() {
    }

    public DbActiveGame(DbClient client, DbQuest quest, Date createdAt) {
        this.client = client;
        this.quest = quest;
        this.createdAt = createdAt;
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
