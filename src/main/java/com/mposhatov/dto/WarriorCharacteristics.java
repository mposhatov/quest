package com.mposhatov.dto;

import com.mposhatov.entity.AttackType;

public class WarriorCharacteristics extends MailCharacteristics {

    private long health;
    private long minDamage;
    private long maxDamage;

    private AttackType attackType;
    private long velocity;

    private long additionalDamagePercent;
    private long criticalDamageChange;
    private long multiplierCriticalDamage;

    private long probableOfEvasion;
    private long physicalBlockPercent;
    private long magicalBlockPercent;

    private long vampirism;
    private long changeOfStun;

    public WarriorCharacteristics() {
    }

    public WarriorCharacteristics(long health, long mana,
                                  long spellPower,
                                  long attack, AttackType attackType, long additionalDamagePercent,
                                  long physicalDefense, long magicDefense,
                                  long minDamage, long maxDamage,
                                  long velocity,
                                  long probableOfEvasion,
                                  long physicalBlockPercent,
                                  long magicalBlockPercent,
                                  long vampirism,
                                  long criticalDamageChange,
                                  long multiplierCriticalDamage,
                                  long changeOfStun) {

        super(mana, spellPower, attack, physicalDefense, magicDefense);
        this.health = health;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.attackType = attackType;
        this.velocity = velocity;
        this.probableOfEvasion = probableOfEvasion;
        this.physicalBlockPercent = physicalBlockPercent;
        this.magicalBlockPercent = magicalBlockPercent;
        this.additionalDamagePercent = additionalDamagePercent;
        this.vampirism = vampirism;
        this.criticalDamageChange = criticalDamageChange;
        this.multiplierCriticalDamage = multiplierCriticalDamage;
        this.changeOfStun = changeOfStun;
    }

    public WarriorCharacteristics addHealth(long health) {
        this.health += health;
        return this;
    }

    public WarriorCharacteristics minusHealth(long health) {
        this.health -= health;
        return this;
    }

    public long getHealth() {
        return health;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public long getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(long minDamage) {
        this.minDamage = minDamage;
    }

    public long getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(long maxDamage) {
        this.maxDamage = maxDamage;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public long getVelocity() {
        return velocity;
    }

    public void setVelocity(long velocity) {
        this.velocity = velocity;
    }

    public long getAdditionalDamagePercent() {
        return additionalDamagePercent;
    }

    public void setAdditionalDamagePercent(long additionalDamagePercent) {
        this.additionalDamagePercent = additionalDamagePercent;
    }

    public long getCriticalDamageChange() {
        return criticalDamageChange;
    }

    public void setCriticalDamageChange(long criticalDamageChange) {
        this.criticalDamageChange = criticalDamageChange;
    }

    public long getMultiplierCriticalDamage() {
        return multiplierCriticalDamage;
    }

    public void setMultiplierCriticalDamage(long multiplierCriticalDamage) {
        this.multiplierCriticalDamage = multiplierCriticalDamage;
    }

    public long getProbableOfEvasion() {
        return probableOfEvasion;
    }

    public void setProbableOfEvasion(long probableOfEvasion) {
        this.probableOfEvasion = probableOfEvasion;
    }

    public long getPhysicalBlockPercent() {
        return physicalBlockPercent;
    }

    public void setPhysicalBlockPercent(long physicalBlockPercent) {
        this.physicalBlockPercent = physicalBlockPercent;
    }

    public long getMagicalBlockPercent() {
        return magicalBlockPercent;
    }

    public void setMagicalBlockPercent(long magicalBlockPercent) {
        this.magicalBlockPercent = magicalBlockPercent;
    }

    public long getVampirism() {
        return vampirism;
    }

    public void setVampirism(long vampirism) {
        this.vampirism = vampirism;
    }

    public long getChangeOfStun() {
        return changeOfStun;
    }

    public void setChangeOfStun(long changeOfStun) {
        this.changeOfStun = changeOfStun;
    }
}
