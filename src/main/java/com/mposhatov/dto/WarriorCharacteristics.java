package com.mposhatov.dto;

import com.mposhatov.entity.AttackType;

public class WarriorCharacteristics extends MainCharacteristics {

    private long health;

    private AttackType attackType;

    private int activatedDefensePercent;

    private int velocity;

    private int additionalDamagePercent;
    private int criticalDamageChange;
    private int multiplierCriticalDamage;

    private int probableOfEvasion;
    private int physicalBlockPercent;
    private int magicalBlockPercent;

    private int vampirism;
    private int changeOfStun;

    public WarriorCharacteristics() {
    }

    public WarriorCharacteristics(long health, long mana,
                                  long spellPower,
                                  long attack, AttackType attackType, int additionalDamagePercent,
                                  long physicalDefense, long magicDefense,
                                  int velocity, int activatedDefensePercent,
                                  int probableOfEvasion,
                                  int physicalBlockPercent,
                                  int magicalBlockPercent,
                                  int vampirism,
                                  int criticalDamageChange,
                                  int multiplierCriticalDamage,
                                  int changeOfStun) {

        super(mana, spellPower, attack, physicalDefense, magicDefense);
        this.health = health;
        this.attackType = attackType;
        this.velocity = velocity;
        this.activatedDefensePercent = activatedDefensePercent;
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

    public WarriorCharacteristics addPhysicalDefense(long physicalDefense) {
        this.physicalDefense += physicalDefense;
        return this;
    }

    public long getHealth() {
        return health;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public int getActivatedDefensePercent() {
        return activatedDefensePercent;
    }

    public void setActivatedDefensePercent(int activatedDefensePercent) {
        this.activatedDefensePercent = activatedDefensePercent;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getAdditionalDamagePercent() {
        return additionalDamagePercent;
    }

    public void setAdditionalDamagePercent(int additionalDamagePercent) {
        this.additionalDamagePercent = additionalDamagePercent;
    }

    public int getCriticalDamageChange() {
        return criticalDamageChange;
    }

    public void setCriticalDamageChange(int criticalDamageChange) {
        this.criticalDamageChange = criticalDamageChange;
    }

    public int getMultiplierCriticalDamage() {
        return multiplierCriticalDamage;
    }

    public void setMultiplierCriticalDamage(int multiplierCriticalDamage) {
        this.multiplierCriticalDamage = multiplierCriticalDamage;
    }

    public int getProbableOfEvasion() {
        return probableOfEvasion;
    }

    public void setProbableOfEvasion(int probableOfEvasion) {
        this.probableOfEvasion = probableOfEvasion;
    }

    public int getPhysicalBlockPercent() {
        return physicalBlockPercent;
    }

    public void setPhysicalBlockPercent(int physicalBlockPercent) {
        this.physicalBlockPercent = physicalBlockPercent;
    }

    public int getMagicalBlockPercent() {
        return magicalBlockPercent;
    }

    public void setMagicalBlockPercent(int magicalBlockPercent) {
        this.magicalBlockPercent = magicalBlockPercent;
    }

    public int getVampirism() {
        return vampirism;
    }

    public void setVampirism(int vampirism) {
        this.vampirism = vampirism;
    }

    public int getChangeOfStun() {
        return changeOfStun;
    }

    public void setChangeOfStun(int changeOfStun) {
        this.changeOfStun = changeOfStun;
    }
}
