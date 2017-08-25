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
    @JoinColumn(name = "SIMPLE_GAME_ID", nullable = false)
    private SimpleGame simpleGame;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "STEP_ID", nullable = false)
    private DbStep step;

    @Column(name = "CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "EVENTS_OF_ACTIVE_GAME",
            joinColumns = {@JoinColumn(name = "ACTIVE_GAME_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "EVENT_ID", nullable = false)})
    private List<DbEvent> completedEvents = new ArrayList<>();

    protected DbActiveGame() {
    }

    public DbActiveGame(Long clientId, SimpleGame simpleGame, DbStep step) {
        this.clientId = clientId;
        this.simpleGame = simpleGame;
        this.step = step;
        this.createdAt = new Date();
    }

    public DbActiveGame setStep(DbStep step) {
        this.step = step;
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
        final List<DbAnswer> allAnswers = this.step.getAnswers();
        return allAnswers.stream()
                .filter(o -> getSubjects().containsAll(o.getRequiredSubjects()))
                .filter(o -> getCompletedEvents().containsAll(o.getRequiredEvents()))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public DbStep getStep() {
        return step;
    }

    public SimpleGame getSimpleGame() {
        return simpleGame;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Long getClientId() {
        return clientId;
    }

    public List<DbEvent> getCompletedEvents() {
        return completedEvents;
    }
}
