package com.mposhatov.dto;

public class QuantifiableWarriorCharacteristics extends MainCharacteristics {

    protected int health;
    protected int activatedDefensePercent;
    protected int velocity;
    protected int probableOfEvasion;
    protected int physicalBlockPercent;
    protected int magicalBlockPercent;
    protected int additionalDamagePercent;
    protected int vampirism;
    protected int criticalDamageChange;
    protected int criticalDamageMultiplier;
    protected int changeOfStun;

    public QuantifiableWarriorCharacteristics() {
    }

    public QuantifiableWarriorCharacteristics(int mana, int spellPower, int attack, int physicalDefense, int magicDefense, int health, int activatedDefensePercent, int velocity, int probableOfEvasion, int physicalBlockPercent, int magicalBlockPercent, int additionalDamagePercent, int vampirism, int criticalDamageChange, int criticalDamageMultiplier, int changeOfStun) {
        super(mana, spellPower, attack, physicalDefense, magicDefense);
        this.health = health;
        this.activatedDefensePercent = activatedDefensePercent;
        this.velocity = velocity;
        this.probableOfEvasion = probableOfEvasion;
        this.physicalBlockPercent = physicalBlockPercent;
        this.magicalBlockPercent = magicalBlockPercent;
        this.additionalDamagePercent = additionalDamagePercent;
        this.vampirism = vampirism;
        this.criticalDamageChange = criticalDamageChange;
        this.criticalDamageMultiplier = criticalDamageMultiplier;
        this.changeOfStun = changeOfStun;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public int getAdditionalDamagePercent() {
        return additionalDamagePercent;
    }

    public void setAdditionalDamagePercent(int additionalDamagePercent) {
        this.additionalDamagePercent = additionalDamagePercent;
    }

    public int getVampirism() {
        return vampirism;
    }

    public void setVampirism(int vampirism) {
        this.vampirism = vampirism;
    }

    public int getCriticalDamageChange() {
        return criticalDamageChange;
    }

    public void setCriticalDamageChange(int criticalDamageChange) {
        this.criticalDamageChange = criticalDamageChange;
    }

    public int getCriticalDamageMultiplier() {
        return criticalDamageMultiplier;
    }

    public void setCriticalDamageMultiplier(int criticalDamageMultiplier) {
        this.criticalDamageMultiplier = criticalDamageMultiplier;
    }

    public int getChangeOfStun() {
        return changeOfStun;
    }

    public void setChangeOfStun(int changeOfStun) {
        this.changeOfStun = changeOfStun;
    }
}
