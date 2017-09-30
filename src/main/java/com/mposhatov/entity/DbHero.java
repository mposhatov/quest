package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HERO")
public class DbHero extends Creature {

    @Column(name = "AVAILABLE_CHARACTERISTICS", nullable = false)
    private long availableCharacteristics;

    @Column(name = "AVAILABLE_SKILLS", nullable = false)
    private long availableSkills;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "HERO_CHARACTERISTICS_ID", nullable = false)
    private DbHeroCharacteristics heroCharacteristics;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "INVENTORY_ID", nullable = false)
    private DbInventory inventory;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "hero")
    private List<DbWarrior> warriors = new ArrayList<>();

    public DbHero() {
        super();
        addAvailableCharacteristicsByLevel();
        addAvailableSkillsByLevel();

        this.heroCharacteristics = new DbHeroCharacteristics();

        this.inventory = new DbInventory();
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

    public DbHero addWarrior(DbWarrior warrior) {
        this.warriors.add(warrior);
        return this;
    }

    public long getAvailableCharacteristics() {
        return availableCharacteristics;
    }

    public long getAvailableSkills() {
        return availableSkills;
    }

    public DbHeroCharacteristics getHeroCharacteristics() {
        return heroCharacteristics;
    }

    public DbInventory getInventory() {
        return inventory;
    }

    public List<DbWarrior> getWarriors() {
        return warriors;
    }
}
