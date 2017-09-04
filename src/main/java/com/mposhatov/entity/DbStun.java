package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "STUN")
public class DbStun {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PERCENT", nullable = false)
    private long percent;

    @Column(name = "MOVES", nullable = false)
    private long moves;

    private DbStun() {
    }

    public DbStun(long percent, long moves) {
        this.percent = percent;
        this.moves = moves;
    }

    public Long getId() {
        return id;
    }

    public long getPercent() {
        return percent;
    }

    public long getMoves() {
        return moves;
    }
}
