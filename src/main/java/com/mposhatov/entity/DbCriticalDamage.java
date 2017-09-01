package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "CRITICAL_GAME")
public class DbCriticalDamage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PERCENT", nullable = false)
    private long percent;

    @Column(name = "MULTIPLIER", nullable = false)
    private long multiplier;

    public DbCriticalDamage(long percent, long multiplier) {
        this.percent = percent;
        this.multiplier = multiplier;
    }

    public Long getId() {
        return id;
    }

    public long getPercent() {
        return percent;
    }

    public long getMultiplier() {
        return multiplier;
    }
}
