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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QUEST_ID", nullable = false)
    private DbQuest quest;

    @Column(name = "CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    //todo add many to many event - game, subject - game
    @OneToMany(mappedBy = "activeGame", fetch = FetchType.LAZY)
    private List<DbSubject> subjects = new ArrayList<>();

    @OneToMany(mappedBy = "activeGame", fetch = FetchType.LAZY)
    private List<DbEvent> events = new ArrayList<>();

    public DbActiveGame() {
    }

    public DbActiveGame(DbClient client, DbQuest quest, Date createdAt) {
        this.client = client;
        this.quest = quest;
        this.createdAt = createdAt;
    }

    public void addSubject(DbSubject subject) {
        subjects.add(subject);
    }

    public void addSubjects(Collection<DbSubject> subjects) {
        subjects.addAll(subjects);
    }

    public void addEvent(DbEvent event) {
        events.add(event);
    }

    public void addEvents(Collection<DbEvent> events) {
        events.addAll(events);
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

    public List<DbEvent> getEvents() {
        return events;
    }
}
