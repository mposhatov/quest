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
        this.hero.getCharacteristics().addAttack(subject.getGivingCharacteristics().getAttack());
        this.hero.getCharacteristics().addPhysicalDefense(subject.getGivingCharacteristics().getPhysicalDefense());

        this.hero.getCharacteristics().addSpellPower(subject.getGivingCharacteristics().getSpellPower());
        this.hero.getCharacteristics().addStrength(subject.getGivingCharacteristics().getStrength());
        this.hero.getCharacteristics().addKnowledge(subject.getGivingCharacteristics().getKnowledge());

        this.hero.getCharacteristics().addDamage(
                subject.getGivingCharacteristics().getMinDamage(),
                subject.getGivingCharacteristics().getMaxDamage());

        this.hero.getCharacteristics().addProbableOfEvasion(subject.getGivingCharacteristics().getProbableOfEvasion());
        this.hero.getCharacteristics().addBlockPercent(subject.getGivingCharacteristics().getBlockPercent());
        this.hero.getCharacteristics().addAdditionalDamagePercent(subject.getGivingCharacteristics().getAdditionalDamagePercent());
        this.hero.getCharacteristics().addVampirism(subject.getGivingCharacteristics().getVampirism());
        this.hero.getCharacteristics().addChangeOfDoubleDamage(subject.getGivingCharacteristics().getChangeOfDoubleDamage());
        this.hero.getCharacteristics().addChangeOfStun(subject.getGivingCharacteristics().getChangeOfStun());
        return this;
    }

    public DbInventory addSubjects(List<DbSubject> subjects) {
        subjects.forEach(this::addSubject);
        return this;
    }

    public DbInventory minusSubject(DbSubject subject) {
        this.subjects.add(subject);
        this.hero.getCharacteristics().minusAttack(subject.getGivingCharacteristics().getAttack());
        this.hero.getCharacteristics().minusPhysicalDefense(subject.getGivingCharacteristics().getPhysicalDefense());

        this.hero.getCharacteristics().minusSpellPower(subject.getGivingCharacteristics().getSpellPower());
        this.hero.getCharacteristics().minusStrength(subject.getGivingCharacteristics().getStrength());
        this.hero.getCharacteristics().minusKnowledge(subject.getGivingCharacteristics().getKnowledge());

        this.hero.getCharacteristics().minusDamage(
                subject.getGivingCharacteristics().getMinDamage(),
                subject.getGivingCharacteristics().getMaxDamage());

        this.hero.getCharacteristics().minusProbableOfEvasion(subject.getGivingCharacteristics().getProbableOfEvasion());
        this.hero.getCharacteristics().minusBlockPercent(subject.getGivingCharacteristics().getBlockPercent());
        this.hero.getCharacteristics().minusAdditionalDamagePercent(subject.getGivingCharacteristics().getAdditionalDamagePercent());
        this.hero.getCharacteristics().minusVampirism(subject.getGivingCharacteristics().getVampirism());
        this.hero.getCharacteristics().minusChangeOfDoubleDamage(subject.getGivingCharacteristics().getChangeOfDoubleDamage());
        this.hero.getCharacteristics().minusChangeOfStun(subject.getGivingCharacteristics().getChangeOfStun());
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
