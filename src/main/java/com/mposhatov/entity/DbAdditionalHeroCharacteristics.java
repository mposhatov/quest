package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "ADDITIONAL_HERO_CHARACTERISTICS")
public class DbAdditionalHeroCharacteristics extends MainCharacteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected DbAdditionalHeroCharacteristics() {
    }

    public DbAdditionalHeroCharacteristics(long attack, long physicalDefense, long magicDefense) {
        super(attack, physicalDefense, magicDefense);
    }

    public Long getId() {
        return id;
    }
}
