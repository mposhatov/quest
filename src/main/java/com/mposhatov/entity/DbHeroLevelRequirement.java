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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDITIONAL_HERO_POINT_ID", nullable = true)
    private DbAdditionalHeroPoint additionalHeroPoint;

    protected DbHeroLevelRequirement() {
    }

    public DbHeroLevelRequirement(Long level, Long requirementExperience) {
        this(level, requirementExperience, null);
    }

    public DbHeroLevelRequirement(Long level, Long requirementExperience, DbAdditionalHeroPoint additionalHeroPoint) {
        this.level = level;
        this.requirementExperience = requirementExperience;
        this.additionalHeroPoint = additionalHeroPoint;
    }

    public Long getLevel() {
        return level;
    }

    public Long getRequirementExperience() {
        return requirementExperience;
    }

    public DbAdditionalHeroPoint getAdditionalHeroPoint() {
        return additionalHeroPoint;
    }
}
