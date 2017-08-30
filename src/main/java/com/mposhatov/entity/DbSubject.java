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

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 100, nullable = false)
    private String description;

    @Column(name = "PICTURE_NAME", nullable = false)
    private String pictureName;

    @Column(name = "PRICE_OF_GOLDEN_COINS", nullable = false)
    private long priceOfGoldenCoins;

    @Column(name = "PRICE_OF_DIAMONDS", nullable = false)
    private long priceOfDiamonds;

    @Column(name = "ATTACK", nullable = false)
    private long attack;

    @Column(name = "DEFENSE", nullable = false)
    private long defense;

    @Column(name = "SPELL_POWER", nullable = false)
    private long spellPower;

    @Column(name = "KNOWLEDGE", nullable = false)
    private long knowledge;

    @Column(name = "STRENGTH", nullable = false)
    private long strength;

    @Column(name = "MIN_DAMAGE", nullable = false)
    private long minDamage;

    @Column(name = "MAX_DAMAGE", nullable = false)
    private long maxDamage;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SUBJECTS_OF_CLIENTS",
            joinColumns = {@JoinColumn(name = "SUBJECT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "CLIENT_ID", nullable = false)})
    private List<DbClient> heldByClients = new ArrayList<>();

    protected DbSubject() {
    }

    public DbSubject(String name, String description, String pictureName,
                     long priceOfGoldenCoins, long priceOfDiamonds,
                     long attack, long defense, long spellPower, long knowledge, long strength,
                     long minDamage, long maxDamage) {
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
        this.priceOfGoldenCoins = priceOfGoldenCoins;
        this.priceOfDiamonds = priceOfDiamonds;
        this.attack = attack;
        this.defense = defense;
        this.spellPower = spellPower;
        this.knowledge = knowledge;
        this.strength = strength;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureName() {
        return pictureName;
    }

    public long getPriceOfGoldenCoins() {
        return priceOfGoldenCoins;
    }

    public long getPriceOfDiamonds() {
        return priceOfDiamonds;
    }

    public long getAttack() {
        return attack;
    }

    public long getDefense() {
        return defense;
    }

    public long getSpellPower() {
        return spellPower;
    }

    public long getKnowledge() {
        return knowledge;
    }

    public long getStrength() {
        return strength;
    }

    public long getMinDamage() {
        return minDamage;
    }

    public long getMaxDamage() {
        return maxDamage;
    }

    public List<DbClient> getHeldByClients() {
        return heldByClients;
    }
}
