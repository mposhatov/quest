package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "LEVEL")
public class DbLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "WARRIOR_NAME", nullable = false)
    private String warriorName;

    @Column(name = "LEVEL", nullable = false)
    private long level;

    @Column(name = "REQUIREMENT_EXPERIENCE", nullable = false)
    private long requirementExperience;

    protected DbLevel() {
    }

    public DbLevel(String warriorName, long level, long requirementExperience) {
        this.warriorName = warriorName;
        this.level = level;
        this.requirementExperience = requirementExperience;
    }

    public Long getId() {
        return id;
    }

    public String getWarriorName() {
        return warriorName;
    }

    public long getLevel() {
        return level;
    }

    public long getRequirementExperience() {
        return requirementExperience;
    }
}
