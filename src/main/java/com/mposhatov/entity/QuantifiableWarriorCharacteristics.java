package com.mposhatov.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class QuantifiableWarriorCharacteristics extends MainCharacteristics {

    @Column(name = "HEALTH", nullable = false)
    protected long health;

    @Column(name = "ACTIVATED_DEFENSE_PERCENT", nullable = false)
    protected int activatedDefensePercent;

    @Column(name = "VELOCITY", nullable = false)
    protected int velocity;

    @Column(name = "PROBABILITY_OF_EVASION", nullable = false)
    protected int probableOfEvasion;

    @Column(name = "PHYSICAL_BLOCK_PERCENT", nullable = false)
    protected int physicalBlockPercent;

    @Column(name = "MAGICAL_BLOCK_PERCENT", nullable = false)
    protected int magicalBlockPercent;

    @Column(name = "ADDITIONAL_DAMAGE_PERCENT", nullable = false)
    protected int additionalDamagePercent;

    @Column(name = "VAMPIRISM", nullable = false)
    protected int vampirism;

    @Column(name = "CRITICAL_DAMAGE_CHANGE", nullable = false)
    protected int criticalDamageChange;

    @Column(name = "CRITICAL_DAMAGE_MULTIPLIER", nullable = false)
    protected int criticalDamageMultiplier;

    @Column(name = "CHANGE_OF_STUN", nullable = false)
    protected int changeOfStun;

    protected QuantifiableWarriorCharacteristics() {
    }

    public QuantifiableWarriorCharacteristics(long attack, long physicalDefense, long magicDefense, long health, int velocity, int activatedDefensePercent) {
        super(attack, physicalDefense, magicDefense);
        this.health = health;
        this.velocity = velocity;
        this.activatedDefensePercent = activatedDefensePercent;
    }

    public QuantifiableWarriorCharacteristics addHealth(long health) {
        this.health += health;
        return this;
    }

    public QuantifiableWarriorCharacteristics addProbableOfEvasion(int probableOfEvasion) {
        this.probableOfEvasion += probableOfEvasion;
        return this;
    }

    public QuantifiableWarriorCharacteristics addPhysicalBlockPercent(int physicalBlockPercent) {
        this.physicalBlockPercent += physicalBlockPercent;
        return this;
    }

    public QuantifiableWarriorCharacteristics addMagicalBlockPercent(int magicalBlockPercent) {
        this.magicalBlockPercent += magicalBlockPercent;
        return this;
    }

    public QuantifiableWarriorCharacteristics addAdditionalDamagePercent(int additionalDamagePercent) {
        this.additionalDamagePercent += additionalDamagePercent;
        return this;
    }

    public QuantifiableWarriorCharacteristics addVampirism(int vampirism) {
        this.vampirism += vampirism;
        return this;
    }

    public QuantifiableWarriorCharacteristics addChangeOfDoubleDamage(int changeOfDoubleDamage) {
        this.criticalDamageChange += changeOfDoubleDamage;
        return this;
    }

    public QuantifiableWarriorCharacteristics addChangeOfStun(int changeOfStun) {
        this.changeOfStun += changeOfStun;
        return this;
    }

    public QuantifiableWarriorCharacteristics addVelocity(int velocity) {
        this.velocity += velocity;
        return this;
    }

    public QuantifiableWarriorCharacteristics addCriticalDamageChange(int criticalDamageChange) {
        this.criticalDamageChange += criticalDamageChange;
        return this;
    }

    public QuantifiableWarriorCharacteristics addMultiplierCriticalDamage(int multiplierCriticalDamage) {
        this.criticalDamageMultiplier += multiplierCriticalDamage;
        return this;
    }

    public QuantifiableWarriorCharacteristics addActivatedDefensePercent(int activatedDefensePercent) {
        this.activatedDefensePercent += activatedDefensePercent;
        return this;
    }

    public long getHealth() {
        return health;
    }

    public int getActivatedDefensePercent() {
        return activatedDefensePercent;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getProbableOfEvasion() {
        return probableOfEvasion;
    }

    public int getPhysicalBlockPercent() {
        return physicalBlockPercent;
    }

    public int getMagicalBlockPercent() {
        return magicalBlockPercent;
    }

    public int getAdditionalDamagePercent() {
        return additionalDamagePercent;
    }

    public int getVampirism() {
        return vampirism;
    }

    public int getCriticalDamageChange() {
        return criticalDamageChange;
    }

    public int getCriticalDamageMultiplier() {
        return criticalDamageMultiplier;
    }

    public int getChangeOfStun() {
        return changeOfStun;
    }
}
