package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SUBJECT")
public class DbSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "MAIN", nullable = true)
    private boolean main;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "SUBJECT_DESCRIPTION_ID", nullable = false)
    private DbSubjectDescription subjectDescription;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "INVENTORY_ID", nullable = false)
    private DbInventory inventory;

    public DbSubject(boolean main, DbSubjectDescription subjectDescription, DbInventory inventory) {
        if (main) {
            setMain();
        }
        this.subjectDescription = subjectDescription;
        this.inventory = inventory;
    }

    public DbSubject setMain() {
        this.main = true;
        CharacteristicsMerge.mapPlusHeroCharacteristics(
                this.inventory.getHero().getHeroCharacteristics(),
                this.subjectDescription.getHeroCharacteristics());
        return this;
    }

    public DbSubject setNotMain() {
        if(this.main) {
            this.main = false;
            CharacteristicsMerge.mapMinusHeroCharacteristics(
                    this.inventory.getHero().getHeroCharacteristics(),
                    this.subjectDescription.getHeroCharacteristics());
        }
        return this;
    }

    public Long getId() {
        return id;
    }

    public boolean isMain() {
        return main;
    }

    public DbSubjectDescription getSubjectDescription() {
        return subjectDescription;
    }

    public DbInventory getInventory() {
        return inventory;
    }
}
