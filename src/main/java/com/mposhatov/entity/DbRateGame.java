package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "RATE_GAME")
public class DbRateGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 30, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 200, nullable = false)
    private String description;

    protected DbRateGame() {
    }

    public DbRateGame(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
