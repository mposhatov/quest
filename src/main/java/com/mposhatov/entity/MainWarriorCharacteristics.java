package com.mposhatov.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class MainWarriorCharacteristics extends MainCharacteristics {

    @Column(name = "HEALTH", nullable = false)
    protected long health;

    @Column(name = "MIN_DAMAGE", nullable = false)
    protected long minDamage;

    @Column(name = "MAX_DAMAGE", nullable = false)
    protected long maxDamage;

    @Column(name = "VELOCITY", nullable = false)
    protected long velocity;

    @Column(name = "PROBABILITY_OF_EVASION", nullable = false)
    protected long probableOfEvasion;

    @Column(name = "PHYSICAL_BLOCK_PERCENT", nullable = false)
    protected long physicalBlockPercent;

    @Column(name = "MAGICAL_BLOCK_PERCENT", nullable = false)
    protected long magicalBlockPercent;

    @Column(name = "ADDITIONAL_DAMAGE_PERCENT", nullable = false)
    protected long additionalDamagePercent;

    @Column(name = "VAMPIRISM", nullable = false)
    protected long vampirism;

    @Column(name = "CRITICAL_DAMAGE_CHANGE", nullable = false)
    protected long criticalDamageChange;

    @Column(name = "CRITICAL_DAMAGE_MULTIPLIER", nullable = false)
    protected long criticalDamageMultiplier;

    @Column(name = "CHANGE_OF_STUN", nullable = false)
    protected long changeOfStun;

    protected MainWarriorCharacteristics() {
    }

    public MainWarriorCharacteristics(long attack,
                                      long physicalDefense, long magicDefense,
                                      long health,
                                      long minDamage, long maxDamage,
                                      long velocity) {

        super(attack, physicalDefense, magicDefense);
        this.health = health;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.velocity = velocity;
    }

    public MainWarriorCharacteristics addHealth(long health) {
        this.health += health;
        return this;
    }

    public MainWarriorCharacteristics addProbableOfEvasion(long probableOfEvasion) {
        this.probableOfEvasion += probableOfEvasion;
        return this;
    }

    public MainWarriorCharacteristics addPhysicalBlockPercent(long physicalBlockPercent) {
        this.physicalBlockPercent += physicalBlockPercent;
        return this;
    }

    public MainWarriorCharacteristics addMagicalBlockPercent(long magicalBlockPercent) {
        this.magicalBlockPercent += magicalBlockPercent;
        return this;
    }

    public MainWarriorCharacteristics addAdditionalDamagePercent(long additionalDamagePercent) {
        this.additionalDamagePercent += additionalDamagePercent;
        return this;
    }

    public MainWarriorCharacteristics addVampirism(long vampirism) {
        this.vampirism += vampirism;
        return this;
    }

    public MainWarriorCharacteristics addChangeOfDoubleDamage(long changeOfDoubleDamage) {
        this.criticalDamageChange += changeOfDoubleDamage;
        return this;
    }

    public MainWarriorCharacteristics addChangeOfStun(long changeOfStun) {
        this.changeOfStun += changeOfStun;
        return this;
    }

    public MainWarriorCharacteristics addDamage(long minDamage, long maxDamage) {
        this.minDamage += minDamage;
        this.maxDamage += maxDamage;
        return this;
    }

    public MainWarriorCharacteristics addVelocity(long velocity) {
        this.velocity += velocity;
        return this;
    }

    public MainWarriorCharacteristics addCriticalDamageChange(long criticalDamageChange) {
        this.criticalDamageChange += criticalDamageChange;
        return this;
    }

    public MainWarriorCharacteristics addMultiplierCriticalDamage(long multiplierCriticalDamage) {
        this.criticalDamageMultiplier += multiplierCriticalDamage;
        return this;
    }

    public long getHealth() {
        return health;
    }

    public long getMinDamage() {
        return minDamage;
    }

    public long getMaxDamage() {
        return maxDamage;
    }

    public long getVelocity() {
        return velocity;
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

    public long getCriticalDamageMultiplier() {
        return criticalDamageMultiplier;
    }

    public long getChangeOfStun() {
        return changeOfStun;
    }
}
