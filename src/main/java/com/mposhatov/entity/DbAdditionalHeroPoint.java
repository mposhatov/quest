package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "ADDITIONAL_HERO_POINT")
public class DbAdditionalHeroPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "AVAILABLE_CHARACTERISTICS", nullable = false)
    private long availableCharacteristics;

    @Column(name = "AVAILABLE_SKILLS", nullable = false)
    private long availableSkills;

    @Column(name = "AVAILABLE_SLOTS", nullable = false)
    private long availableSlots;

    protected DbAdditionalHeroPoint() {
    }

    public DbAdditionalHeroPoint(long availableCharacteristics, long availableSkills, long availableSlots) {
        this.availableCharacteristics = availableCharacteristics;
        this.availableSkills = availableSkills;
        this.availableSlots = availableSlots;
    }

    public Long getId() {
        return id;
    }

    public long getAvailableCharacteristics() {
        return availableCharacteristics;
    }

    public long getAvailableSkills() {
        return availableSkills;
    }

    public long getAvailableSlots() {
        return availableSlots;
    }
}
