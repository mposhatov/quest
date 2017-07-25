package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "ANONYMOUS_ACTIVE_GAME")
public class DbAnonymousActiveGame extends ActiveGame {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ANONYMOUS_CLIENT_ID", nullable = false)
    private DbAnonymousClient client;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "SUBJECTS_OF_ANONYMOUS_ACTIVE_GAME",
            joinColumns = {@JoinColumn(name = "ANONYMOUS_ACTIVE_GAME_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SUBJECT_ID", nullable = false)})
    private List<DbSubject> subjects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "EVENTS_OF_ANONYMOUS_ACTIVE_GAME",
            joinColumns = {@JoinColumn(name = "ANONYMOUS_ACTIVE_GAME_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "EVENT_ID", nullable = false)})
    private List<DbEvent> completedEvents = new ArrayList<>();

    protected DbAnonymousActiveGame() {
    }

    public DbAnonymousActiveGame(DbAnonymousClient client, DbQuest quest, DbStep step) {
        super(quest, step);
        this.client = client;
    }

    public ActiveGame addSubject(DbSubject subject) {
        this.subjects.add(subject);
        return this;
    }

    public ActiveGame addSubjects(Collection<DbSubject> subjects) {
        this.subjects.addAll(subjects);
        return this;
    }

    public ActiveGame addEvent(DbEvent event) {
        this.completedEvents.add(event);
        return this;
    }

    public ActiveGame addEvents(Collection<DbEvent> events) {
        this.completedEvents.addAll(events);
        return this;
    }

    public DbAnonymousClient getClient() {
        return client;
    }

    public List<DbSubject> getSubjects() {
        return subjects;
    }

    public List<DbEvent> getCompletedEvents() {
        return completedEvents;
    }
}
