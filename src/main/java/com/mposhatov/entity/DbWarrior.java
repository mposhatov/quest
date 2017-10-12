package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "WARRIOR")
public class DbWarrior extends Creature {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HERO_ID", nullable = false)
    private DbHero hero;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "WARRIOR_CHARACTERISTICS_ID", nullable = false)
    private DbWarriorCharacteristics warriorCharacteristics;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "WARRIOR_DESCRIPTION_ID", nullable = false)
    private DbWarriorDescription creaturesDescription;

    @Column(name = "MAIN")
    private boolean main;

    protected DbWarrior() {
        super();
    }

    public DbWarrior(DbHero hero, DbWarriorDescription creaturesDescription) {
        super();
        this.warriorCharacteristics = creaturesDescription.getStartWarriorCharacteristics();
        this.hero = hero;
        this.creaturesDescription = creaturesDescription;
    }

    public DbWarrior upLevel() {
        this.level++;
        CharacteristicsMerge.mapPlusWarriorCharacteristics(
                this.warriorCharacteristics, creaturesDescription.getWarriorCharacteristicsByLevel());
        return this;
    }

    public DbWarrior setMain() {
        this.main = true;
        return this;
    }

    public DbHero getHero() {
        return hero;
    }

    public DbWarriorDescription getCreaturesDescription() {
        return creaturesDescription;
    }

    public DbWarriorCharacteristics getWarriorCharacteristics() {
        return warriorCharacteristics;
    }

    public boolean isMain() {
        return main;
    }
}
