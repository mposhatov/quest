package com.mposhatov.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ACTIVE_RATE_GAME")
public class DbActiveRateGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    @Column(name = "STEP_STARTED_AT", nullable = false)
    private Date stepStartedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "activeRateGame")
    private List<DbClient> clients;

    protected DbActiveRateGame() {
    }

    public DbActiveRateGame(List<DbClient> clients) {
        final Date now = new Date();
        this.createdAt = now;
        this.stepStartedAt = now;
        clients.forEach(client -> client.setActiveRateGame(this));
    }

    public Long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getStepStartedAt() {
        return stepStartedAt;
    }

    public List<DbClient> getClients() {
        return clients;
    }
}
