package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "ADDITIONAL_WARRIOR_CHARACTERISTICS")
public class DbAdditionalWarriorCharacteristics extends MainWarriorCharacteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected DbAdditionalWarriorCharacteristics() {
    }

    public DbAdditionalWarriorCharacteristics(long attack, long physicalDefense, long magicDefense, long health,
                                              long minDamage, long maxDamage, long velocity) {

        super(attack, physicalDefense, magicDefense, health, minDamage, maxDamage, velocity);
    }
}
