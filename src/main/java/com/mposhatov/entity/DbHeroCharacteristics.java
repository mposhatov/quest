package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "HERO_CHARACTERISTICS")
public class DbHeroCharacteristics extends MainCharacteristics {

//    @Id
//    @GeneratedValue(generator = "hero")
//    @GenericGenerator(name = "hero", strategy = "foreign", parameters = {@org.hibernate.annotations.Parameter(name = "property", value = "hero")})
//    @Column(name = "HERO_ID")
//    private Long heroId;
//
//    @MapsId
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "HERO_ID", nullable = false)
//    private DbHero hero;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    protected DbHeroCharacteristics() {
    }

    public DbHeroCharacteristics(long attack, long physicalDefense, long magicDefense) {
        super(attack, physicalDefense, magicDefense);
    }

    public Long getId() {
        return id;
    }
}
