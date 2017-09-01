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
    private DbClient client;

    @Column(name = "GOLDEN_COINS", nullable = false)
    private long goldenCoins;

    @Column(name = "DIAMONDS", nullable = false)
    private long diamonds;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SUBJECTS_OF_CLIENTS",
            joinColumns = {@JoinColumn(name = "CLIENT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SUBJECT_ID", nullable = false)})
    private List<DbSubject> subjects = new ArrayList<>();

    protected DbInventory() {
    }

    public DbInventory(DbClient client, long goldenCoins, long diamonds, List<DbSubject> subjects) {
        this.client = client;
        this.goldenCoins = goldenCoins;
        this.diamonds = diamonds;
        this.subjects = subjects;
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
        this.client.getCharacteristics().addAttack(subject.getAttack());
        this.client.getCharacteristics().addPhysicalDefense(subject.getPhysycalDefense());

        this.client.getCharacteristics().addSpellPower(subject.getSpellPower());
        this.client.getCharacteristics().addStrength(subject.getStrength());
        this.client.getCharacteristics().addKnowledge(subject.getKnowledge());

        this.client.getCharacteristics().addDamage(subject.getMinDamage(), subject.getMaxDamage());

        return this;
    }

    public DbInventory addSubjects(List<DbSubject> subjects) {
        subjects.forEach(this::addSubject);
        return this;
    }

    public DbInventory minusSubject(DbSubject subject) {
        this.subjects.remove(subject);
        this.client.getCharacteristics().minusAttack(subject.getAttack());
        this.client.getCharacteristics().minusPhysicalDefense(subject.getPhysycalDefense());

        this.client.getCharacteristics().minusSpellPower(subject.getSpellPower());
        this.client.getCharacteristics().minusStrength(subject.getStrength());
        this.client.getCharacteristics().minusKnowledge(subject.getKnowledge());

        this.client.getCharacteristics().minusDamage(subject.getMinDamage(), subject.getMaxDamage());

        return this;
    }

    public DbInventory minusSubjects(List<DbSubject> subjects) {
        subjects.forEach(this::minusSubject);
        return this;
    }

    public Long getId() {
        return id;
    }

    public DbClient getClient() {
        return client;
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
