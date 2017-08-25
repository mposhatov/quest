package com.mposhatov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LEVEL")
public class DbLevel {

    @Id
    private Long id;

    @Column(name = "REQUIREMENT_EXPERIENCE")
    private long requirementExperience;

    protected DbLevel() {
    }

    public DbLevel(Long id, long requirementExperience) {
        this.id = id;
        this.requirementExperience = requirementExperience;
    }

    public Long getId() {
        return id;
    }

    public long getRequirementExperience() {
        return requirementExperience;
    }
}
