package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "CHARACTERISTICS")
public class DbCharacteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "EXPERIENCE", nullable = false)
    private long experience;

    @Column(name = "LEVEL", nullable = false)
    private long level;

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

    @Column(name = "HEALTH", nullable = false)
    private long health;

    @Column(name = "MANA", nullable = false)
    private long mana;

    @Column(name = "MIN_DAMAGE", nullable = false)
    private long minDamage;

    @Column(name = "MAX_DAMAGE", nullable = false)
    private long maxDamage;

    protected DbCharacteristics() {
    }

    public DbCharacteristics(long attack, long defense, long spellPower,
                             long knowledge, long manaByKnowledge,
                             long strength, long healthByStrength,
                             long minDamage, long maxDamage) {
        this.experience = 0;
        this.level = 1;
        this.attack = attack;
        this.defense = defense;
        this.spellPower = spellPower;
        this.knowledge = knowledge;
        this.strength = strength;
        this.health = strength * healthByStrength;
        this.mana = knowledge * manaByKnowledge;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public DbCharacteristics addExperience(long experience) {
        this.experience += experience;
        return this;
    }

    public DbCharacteristics upLevel() {
        this.level++;
        return this;
    }

    public DbCharacteristics addAttack(long attack) {
        this.attack += attack;
        return this;
    }

    public DbCharacteristics addDefense(long defense) {
        this.defense += defense;
        return this;
    }

    public DbCharacteristics addSpellPower(long spellPower) {
        this.spellPower += spellPower;
        return this;
    }

    public DbCharacteristics addKnowledge(long knowledge, long manaByKnowledge) {
        this.knowledge += knowledge;
        this.mana += manaByKnowledge * knowledge;
        return this;
    }

    public DbCharacteristics addStrength(long strength, long healthByStrength) {
        this.strength += strength;
        this.health += healthByStrength * strength;
        return this;
    }

    public DbCharacteristics addDamage(long minDamage, long maxDamage) {
        this.minDamage += minDamage;
        this.maxDamage += maxDamage;
        return this;
    }

    public DbCharacteristics minusDamage(long minDamage, long maxDamage) {
        this.minDamage -= minDamage;
        this.maxDamage -= maxDamage;
        return this;
    }

    public Long getId() {
        return id;
    }

    public long getExperience() {
        return experience;
    }

    public long getLevel() {
        return level;
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

    public long getHealth() {
        return health;
    }

    public long getMana() {
        return mana;
    }

    public long getMinDamage() {
        return minDamage;
    }

    public long getMaxDamage() {
        return maxDamage;
    }
}
