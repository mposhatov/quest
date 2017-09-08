package com.mposhatov.entity;

import javax.persistence.*;

@MappedSuperclass
public class Creature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "EXPERIENCE", nullable = false)
    protected long experience;

    @Column(name = "LEVEL", nullable = false)
    protected long level;

    protected Creature() {
        this.experience = 0;
        this.level = 1;
    }

    public Creature addExperience(long experience) {
        this.experience += experience;
        return this;
    }

    public Long getId() {
        return id;
    }


    public long getExperience() {
        return experience;
    }

    public long getLevel() {
        return level;
    }
}
