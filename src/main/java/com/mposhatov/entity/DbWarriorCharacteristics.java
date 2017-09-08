package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "WARRIOR_CHARACTERISTICS")
public class DbWarriorCharacteristics extends MainCharacteristics {

    @Column(name = "HEALTH", nullable = false)
    private long health;

    @Column(name = "MIN_DAMAGE", nullable = false)
    protected long minDamage;

    @Column(name = "MAX_DAMAGE", nullable = false)
    protected long maxDamage;

    @Column(name = "VELOCITY", nullable = false)
    protected long velocity;

    @Column(name = "PROBABILITY_OF_EVASION", nullable = false)
    private long probableOfEvasion;

    @Column(name = "BLOCK_PERCENT", nullable = false)
    private long blockPercent;

    @Column(name = "ADDITIONAL_DAMAGE_PERCENT", nullable = false)
    private long additionalDamagePercent;

    @Column(name = "VAMPIRISM", nullable = false)
    private long vampirism;

    @Column(name = "CHANGE_OF_DOUBLE_DAMAGE", nullable = false)
    private long changeOfDoubleDamage;

    @Column(name = "CHANGE_OF_STUN", nullable = false)
    private long changeOfStun;

    protected DbWarriorCharacteristics() {
    }

    public DbWarriorCharacteristics(long attack, long physicalDefense, long magicDefense, long health, long minDamage, long maxDamage, long velocity) {
        super(attack, physicalDefense, magicDefense);
        this.health = health;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.velocity = velocity;
    }

    public DbWarriorCharacteristics addHealthByCharacteristic() {
        this.health += 10;
        return this;
    }

    public DbWarriorCharacteristics addHealth(long health) {
        this.health += health;
        return this;
    }

    public DbWarriorCharacteristics minusHealth(long health) {
        this.health -= health;
        return this;
    }

    public DbWarriorCharacteristics addProbableOfEvasion(long probableOfEvasion) {
        this.probableOfEvasion += probableOfEvasion;
        return this;
    }

    public DbWarriorCharacteristics minusProbableOfEvasion(long probableOfEvasion) {
        this.probableOfEvasion -= probableOfEvasion;
        return this;
    }

    public DbWarriorCharacteristics addBlockPercent(long blockPercent) {
        this.blockPercent += blockPercent;
        return this;
    }

    public DbWarriorCharacteristics minusBlockPercent(long blockPercent) {
        this.blockPercent -= blockPercent;
        return this;
    }

    public DbWarriorCharacteristics addAdditionalDamagePercent(long additionalDamagePercent) {
        this.additionalDamagePercent += additionalDamagePercent;
        return this;
    }

    public DbWarriorCharacteristics minusAdditionalDamagePercent(long additionalDamagePercent) {
        this.additionalDamagePercent -= additionalDamagePercent;
        return this;
    }

    public DbWarriorCharacteristics addVampirism(long vampirism) {
        this.vampirism += vampirism;
        return this;
    }

    public DbWarriorCharacteristics minusVampirism(long vampirism) {
        this.vampirism -= vampirism;
        return this;
    }

    public DbWarriorCharacteristics addChangeOfDoubleDamage(long changeOfDoubleDamage) {
        this.changeOfDoubleDamage += changeOfDoubleDamage;
        return this;
    }

    public DbWarriorCharacteristics minusChangeOfDoubleDamage(long changeOfDoubleDamage) {
        this.changeOfDoubleDamage -= changeOfDoubleDamage;
        return this;
    }

    public DbWarriorCharacteristics addChangeOfStun(long changeOfStun) {
        this.changeOfStun += changeOfStun;
        return this;
    }

    public DbWarriorCharacteristics minusChangeOfStun(long changeOfStun) {
        this.changeOfStun -= changeOfStun;
        return this;
    }

    protected DbWarriorCharacteristics addDamage(long minDamage, long maxDamage) {
        this.minDamage += minDamage;
        this.maxDamage += maxDamage;
        return this;
    }

    protected DbWarriorCharacteristics minusDamage(long minDamage, long maxDamage) {
        this.minDamage -= minDamage;
        this.maxDamage -= maxDamage;
        return this;
    }

    public DbWarriorCharacteristics addVelocity(long velocity) {
        this.velocity += velocity;
        return this;
    }

    public DbWarriorCharacteristics minusVelocity(long velocity) {
        this.velocity -= velocity;
        return this;
    }

    public Long getId() {
        return id;
    }

    public long getAttack() {
        return attack;
    }

    public long getPhysicalDefense() {
        return physicalDefense;
    }

    public long getMagicDefense() {
        return magicDefense;
    }

    public long getSpellPower() {
        return spellPower;
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

    public long getProbableOfEvasion() {
        return probableOfEvasion;
    }

    public long getBlockPercent() {
        return blockPercent;
    }

    public long getAdditionalDamagePercent() {
        return additionalDamagePercent;
    }

    public long getVampirism() {
        return vampirism;
    }

    public long getChangeOfDoubleDamage() {
        return changeOfDoubleDamage;
    }

    public long getChangeOfStun() {
        return changeOfStun;
    }

    public long getVelocity() {
        return velocity;
    }
}
