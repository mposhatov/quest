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

    public DbAdditionalHeroCharacteristics(int attack, int physicalDefense, int magicDefense) {
        super(attack, physicalDefense, magicDefense);
    }

    public Long getId() {
        return id;
    }
}
