package com.mposhatov.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "INVENTORY")
public class DbInventory {

    @Id
    @GeneratedValue(generator = "hero")
    @GenericGenerator(name = "hero", strategy = "foreign", parameters = {@org.hibernate.annotations.Parameter(name = "property", value = "hero")})
    @Column(name = "HERO_ID")
    private Long heroId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HERO_ID", nullable = false)
    private DbHero hero;

    @Column(name = "GOLD_COINS", nullable = false)
    private long goldCoins;

    @Column(name = "DIAMONDS", nullable = false)
    private long diamonds;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "inventory")
    private List<DbSubject> subjects = new ArrayList<>();

    protected DbInventory() {

    }

    public DbInventory(DbHero hero) {
        this.hero = hero;
        addGoldCoins(1100);
        addDiamonds(10);
    }

    public DbInventory addGoldCoins(long goldCoins) {
        this.goldCoins += goldCoins;
        return this;
    }

    public DbInventory minusGoldCoins(long goldCoins) {
        this.goldCoins -= goldCoins;
        return this;
    }

    public DbInventory addDiamonds(long diamonds) {
        this.diamonds += diamonds;
        return this;
    }

    public DbInventory minusDiamonds(long diamonds) {
        this.diamonds -= diamonds;
        return this;
    }

    public DbInventory addSubject(DbSubject subject) {

        subject.setInventory(this);

        this.subjects.add(subject);

        final BodyPart bodyPart = subject.getSubjectDescription().getBodyPart();

        final long countMainByBodyPart = subjects.stream()
                .filter(s -> s.getSubjectDescription().getBodyPart().equals(bodyPart))
                .filter(DbSubject::isMain)
                .count();

        if ((bodyPart.equals(BodyPart.FINGER) && countMainByBodyPart < 2)
                || (!bodyPart.equals(BodyPart.FINGER) && countMainByBodyPart < 1)) {

            subject.setMain();

            CharacteristicsMerge.mapPlusHeroCharacteristics(
                    this.hero.getHeroCharacteristics(),
                    subject.getSubjectDescription().getHeroCharacteristics());
        }

        return this;
    }

    public DbInventory minusSubject(DbSubject subject) {
        this.subjects.remove(subject);

        if (subject.isMain()) {
            CharacteristicsMerge.mapMinusHeroCharacteristics(
                    this.hero.getHeroCharacteristics(),
                    subject.getSubjectDescription().getHeroCharacteristics());
        }

        return this;
    }

    public Long getHeroId() {
        return heroId;
    }

    public long getGoldCoins() {
        return goldCoins;
    }

    public long getDiamonds() {
        return diamonds;
    }

    public List<DbSubject> getSubjects() {
        return subjects;
    }

    public DbHero getHero() {
        return hero;
    }
}
