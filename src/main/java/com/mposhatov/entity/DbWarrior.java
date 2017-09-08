package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "WARRIOR")
public class DbWarrior extends Creature {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HERO_ID", nullable = false)
    private DbHero hero;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "WARRIOR_CHARACTERISTICS_ID", nullable = false)
    protected DbWarriorCharacteristics characteristics;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "WARRIOR_DESCRIPTION_ID", nullable = false)
    private DbWarriorDescription creaturesDescription;

    protected DbWarrior() {
        super();
    }

    public DbWarrior(DbWarriorCharacteristics characteristics, DbHero hero, DbWarriorDescription creaturesDescription) {
        super();
        this.characteristics = characteristics;
        this.hero = hero;
        this.creaturesDescription = creaturesDescription;
    }

    public DbWarrior upLevel() {
        this.level++;
        CharacteristicsMerge.mapPlusWarriorCharacteristics(characteristics, creaturesDescription.getCharacteristicsByLevel());
        return this;
    }

    public DbHero getHero() {
        return hero;
    }

    public DbWarriorDescription getCreaturesDescription() {
        return creaturesDescription;
    }

    public DbWarriorCharacteristics getCharacteristics() {
        return characteristics;
    }
}
