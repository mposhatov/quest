package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "WARRIOR_LEVEL_REQUIREMENT")
public class DbWarriorLevelRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "WARRIOR_NAME", nullable = false)
    private String warriorName;

    @Column(name = "LEVEL", nullable = false)
    private Long level;

    @Column(name = "REQUIREMENT_EXPERIENCE", nullable = false)
    private Long requirementExperience;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDITIONAL_WARRIOR_CHARACTERISTICS_ID", nullable = false)
    private DbAdditionalWarriorCharacteristics additionalWarriorCharacteristics;

    protected DbWarriorLevelRequirement() {
    }

    public DbWarriorLevelRequirement(String warriorName, Long level, Long requirementExperience, DbAdditionalWarriorCharacteristics additionalWarriorCharacteristics) {
        this.warriorName = warriorName;
        this.level = level;
        this.requirementExperience = requirementExperience;
        this.additionalWarriorCharacteristics = additionalWarriorCharacteristics;
    }

    public Long getId() {
        return id;
    }

    public String getWarriorName() {
        return warriorName;
    }

    public Long getLevel() {
        return level;
    }

    public Long getRequirementExperience() {
        return requirementExperience;
    }

    public DbAdditionalWarriorCharacteristics getAdditionalWarriorCharacteristics() {
        return additionalWarriorCharacteristics;
    }
}
