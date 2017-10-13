package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "WARRIOR_CHARACTERISTICS")
public class DbWarriorCharacteristics extends MainCharacteristics {

    @Column(name = "HEALTH", nullable = false)
    private long health;

    @Column(name = "MIN_DAMAGE", nullable = false)
    private long minDamage;

    @Column(name = "MAX_DAMAGE", nullable = false)
    private long maxDamage;

    @Convert(converter = AttackTypeConverter.class)
    @Column(name = "ATTACK_TYPE", nullable = false)
    private AttackType attackType;

    @Column(name = "VELOCITY", nullable = false)
    private long velocity;

    @Column(name = "PROBABILITY_OF_EVASION", nullable = false)
    private long probableOfEvasion;

    @Column(name = "PHYSICAL_BLOCK_PERCENT", nullable = false)
    private long physicalBlockPercent;

    @Column(name = "MAGICAL_BLOCK_PERCENT", nullable = false)
    private long magicalBlockPercent;

    @Column(name = "ADDITIONAL_DAMAGE_PERCENT", nullable = false)
    private long additionalDamagePercent;

    @Column(name = "VAMPIRISM", nullable = false)
    private long vampirism;

    @Column(name = "CRITICAL_DAMAGE_CHANGE", nullable = false)
    private long criticalDamageChange;

    @Column(name = "CRITICAL_DAMAGE_MULTIPLIER", nullable = false)
    private long criticalDamageMultiplier;

    @Column(name = "CHANGE_OF_STUN", nullable = false)
    private long changeOfStun;

    protected DbWarriorCharacteristics() {
    }

    public DbWarriorCharacteristics(long attack, AttackType attackType,
                                    long physicalDefense, long magicDefense,
                                    long health,
                                    long minDamage, long maxDamage,
                                    long velocity) {

        super(attack, physicalDefense, magicDefense);
        this.health = health;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.velocity = velocity;
        this.attackType = attackType;
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

    public DbWarriorCharacteristics addPhysicalBlockPercent(long physicalBlockPercent) {
        this.physicalBlockPercent += physicalBlockPercent;
        return this;
    }

    public DbWarriorCharacteristics minusPhysicalBlockPercent(long physicalBlockPercent) {
        this.physicalBlockPercent -= physicalBlockPercent;
        return this;
    }

    public DbWarriorCharacteristics addMagicalBlockPercent(long magicalBlockPercent) {
        this.magicalBlockPercent += magicalBlockPercent;
        return this;
    }

    public DbWarriorCharacteristics minusMagicalBlockPercent(long magicalBlockPercent) {
        this.magicalBlockPercent -= magicalBlockPercent;
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
        this.criticalDamageChange += changeOfDoubleDamage;
        return this;
    }

    public DbWarriorCharacteristics minusChangeOfDoubleDamage(long changeOfDoubleDamage) {
        this.criticalDamageChange -= changeOfDoubleDamage;
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

    public DbWarriorCharacteristics addDamage(long minDamage, long maxDamage) {
        this.minDamage += minDamage;
        this.maxDamage += maxDamage;
        return this;
    }

    public DbWarriorCharacteristics minusDamage(long minDamage, long maxDamage) {
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

    public DbWarriorCharacteristics addCriticalDamageChange(long criticalDamageChange) {
        this.criticalDamageChange += criticalDamageChange;
        return this;
    }

    public DbWarriorCharacteristics minusCriticalDamageChange(long criticalDamageChange) {
        this.criticalDamageChange -= criticalDamageChange;
        return this;
    }

    public DbWarriorCharacteristics addMultiplierCriticalDamage(long multiplierCriticalDamage) {
        this.criticalDamageMultiplier += multiplierCriticalDamage;
        return this;
    }

    public DbWarriorCharacteristics minusMultiplierCriticalDamage(long multiplierCriticalDamage) {
        this.criticalDamageMultiplier -= multiplierCriticalDamage;
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

    public long getPhysicalBlockPercent() {
        return physicalBlockPercent;
    }

    public long getMagicalBlockPercent() {
        return magicalBlockPercent;
    }

    public long getAdditionalDamagePercent() {
        return additionalDamagePercent;
    }

    public long getVampirism() {
        return vampirism;
    }

    public long getCriticalDamageChange() {
        return criticalDamageChange;
    }

    public long getChangeOfStun() {
        return changeOfStun;
    }

    public long getVelocity() {
        return velocity;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public long getCriticalDamageMultiplier() {
        return criticalDamageMultiplier;
    }
}
