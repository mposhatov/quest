package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "HERO")
public class DbHero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "EXPERIENCE", nullable = false)
    private long experience;

    @Column(name = "LEVEL", nullable = false)
    private long level;

    @Column(name = "AVAILABLE_CHARACTERISTICS", nullable = false)
    private long availableCharacteristics;

    @Column(name = "AVAILABLE_SKILLS", nullable = false)
    private long availableSkills;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CHARACTERISTICS_ID", nullable = false)
    private DbCharacteristics characteristics;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "INVENTORY_ID", nullable = false)
    private DbInventory inventory;

    public DbHero() {
        this.experience = 0;
        this.level = 1;

        addAvailableCharacteristicsByLevel();
        addAvailableSkillsByLevel();

        this.characteristics = new DbCharacteristics();
        this.inventory = new DbInventory();
    }

    public DbHero addExperience(long experience) {
        this.experience += experience;
        return this;
    }

    public DbHero upLevel() {
        this.level++;
        addAvailableCharacteristicsByLevel();
        addAvailableSkillsByLevel();
        return this;
    }

    private DbHero addAvailableCharacteristicsByLevel() {
        this.availableCharacteristics += 2;
        return this;
    }


    public DbHero minusAvailableCharacteristics(long availableCharacteristics) {
        this.availableCharacteristics -= availableCharacteristics;
        return this;
    }

    private DbHero addAvailableSkillsByLevel() {
        this.availableSkills += 1;
        return this;
    }

    public DbHero minusAvailableSkills(long availableSkills) {
        this.availableSkills -= availableSkills;
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

    public long getAvailableCharacteristics() {
        return availableCharacteristics;
    }

    public long getAvailableSkills() {
        return availableSkills;
    }

    public DbCharacteristics getCharacteristics() {
        return characteristics;
    }

    public DbInventory getInventory() {
        return inventory;
    }
}
