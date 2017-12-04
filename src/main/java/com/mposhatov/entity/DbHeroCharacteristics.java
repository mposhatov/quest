package com.mposhatov.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "HERO_CHARACTERISTICS")
public class DbHeroCharacteristics extends MainCharacteristics {

    @Id
    @GeneratedValue(generator = "hero")
    @GenericGenerator(name = "hero", strategy = "foreign", parameters = {@org.hibernate.annotations.Parameter(name = "property", value = "hero")})
    @Column(name = "HERO_ID")
    private Long heroId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HERO_ID", nullable = false)
    private DbHero hero;

    protected DbHeroCharacteristics() {
    }

    public DbHeroCharacteristics(DbHero hero, int attack, int physicalDefense, int magicDefense) {
        super(attack, physicalDefense, magicDefense);
        this.hero = hero;
    }

    public Long getHeroId() {
        return heroId;
    }

    public DbHero getHero() {
        return hero;
    }
}
