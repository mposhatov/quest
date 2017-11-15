package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "ADDITIONAL_WARRIOR_CHARACTERISTICS")
public class DbAdditionalWarriorCharacteristics extends QuantifiableWarriorCharacteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected DbAdditionalWarriorCharacteristics() {
    }

    public DbAdditionalWarriorCharacteristics(long attack, long physicalDefense, long magicDefense, long health, int velocity, int activatedDefensePercent) {
        super(attack, physicalDefense, magicDefense, health, velocity, activatedDefensePercent);
    }
}
