package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "HERO_LEVEL_REQUIREMENT")
public class DbHeroLevelRequirement {

    @Id
    @Column(name = "LEVEL")
    private Long level;

    @Column(name = "REQUIREMENT_EXPERIENCE", nullable = false)
    private Long requirementExperience;

    protected DbHeroLevelRequirement() {
    }

    public DbHeroLevelRequirement(Long level, Long requirementExperience) {
        this.level = level;
        this.requirementExperience = requirementExperience;
    }

    public Long getLevel() {
        return level;
    }

    public Long getRequirementExperience() {
        return requirementExperience;
    }
}
