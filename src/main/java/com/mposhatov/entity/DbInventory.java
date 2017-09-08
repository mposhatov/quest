package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "INVENTORY")
public class DbInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "inventory")
    private DbHero hero;

    @Column(name = "GOLDEN_COINS", nullable = false)
    private long goldenCoins;

    @Column(name = "DIAMONDS", nullable = false)
    private long diamonds;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SUBJECTS_OF_CLIENTS",
            joinColumns = {@JoinColumn(name = "CLIENT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SUBJECT_ID", nullable = false)})
    private List<DbSubject> subjects = new ArrayList<>();

    public DbInventory() {
        addGoldenCoins(1000);
        addDiamonds(10);
    }

    public DbInventory addGoldenCoins(long goldenCoins) {
        this.goldenCoins += goldenCoins;
        return this;
    }

    public DbInventory minusGoldenCoins(long goldenCoins) {
        this.goldenCoins -= goldenCoins;
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
        this.subjects.add(subject);
        CharacteristicsMerge.mapPlusHeroCharacteristics(this.hero.getCharacteristics(), subject.getGivingCharacteristics());
        return this;
    }

    public DbInventory addSubjects(List<DbSubject> subjects) {
        subjects.forEach(this::addSubject);
        return this;
    }

    public DbInventory minusSubject(DbSubject subject) {
        this.subjects.add(subject);
        CharacteristicsMerge.mapMinusHeroCharacteristics(this.hero.getCharacteristics(), subject.getGivingCharacteristics());
        return this;
    }

    public DbInventory minusSubjects(List<DbSubject> subjects) {
        subjects.forEach(this::minusSubject);
        return this;
    }

    public Long getId() {
        return id;
    }

    public long getGoldenCoins() {
        return goldenCoins;
    }

    public long getDiamonds() {
        return diamonds;
    }

    public List<DbSubject> getSubjects() {
        return subjects;
    }
}
