package com.mposhatov.dto;

import com.mposhatov.entity.AttackType;

public class WarriorCharacteristics extends QuantifiableWarriorCharacteristics {

    private AttackType attackType;
    private Target target;

    public WarriorCharacteristics() {
    }

    public WarriorCharacteristics(int health, int mana,
                                  int spellPower,
                                  int attack, AttackType attackType, Target target, int additionalDamagePercent,
                                  int physicalDefense, int magicDefense,
                                  int velocity, int activatedDefensePercent,
                                  int probableOfEvasion,
                                  int physicalBlockPercent,
                                  int magicalBlockPercent,
                                  int vampirism,
                                  int criticalDamageChange,
                                  int criticalDamageMultiplier,
                                  int changeOfStun) {

        super(mana, spellPower, attack, physicalDefense, magicDefense, health, activatedDefensePercent, velocity,
                probableOfEvasion, physicalBlockPercent, magicalBlockPercent, additionalDamagePercent, vampirism,
                criticalDamageChange, criticalDamageMultiplier, changeOfStun);

        this.attackType = attackType;
        this.target = target;
    }

    public WarriorCharacteristics addHealth(int health) {
        this.health += health;
        return this;
    }

    public WarriorCharacteristics minusHealth(int health) {
        this.health -= health;
        return this;
    }

    public WarriorCharacteristics addPhysicalDefense(int physicalDefense) {
        this.physicalDefense += physicalDefense;
        return this;
    }

    public WarriorCharacteristics addMana(int mana) {
        this.mana += mana;
        return this;
    }

    public WarriorCharacteristics minusMana(int mana) {
        this.mana -= mana;
        return this;
    }

    public WarriorCharacteristics addAttack(int attack) {
        this.attack += attack;
        return this;
    }

    public WarriorCharacteristics addMagicDefense(int magicDefense) {
        this.magicDefense += magicDefense;
        return this;
    }

    public WarriorCharacteristics addSpellPower(int spellPower) {
        this.spellPower += spellPower;
        return this;
    }

    public WarriorCharacteristics addProbableOfEvasion(int probableOfEvasion) {
        this.probableOfEvasion += probableOfEvasion;
        return this;
    }

    public WarriorCharacteristics addPhysicalBlockPercent(int physicalBlockPercent) {
        this.physicalBlockPercent += physicalBlockPercent;
        return this;
    }

    public WarriorCharacteristics addMagicalBlockPercent(int magicalBlockPercent) {
        this.magicalBlockPercent += magicalBlockPercent;
        return this;
    }

    public WarriorCharacteristics addAdditionalDamagePercent(int additionalDamagePercent) {
        this.additionalDamagePercent += additionalDamagePercent;
        return this;
    }

    public WarriorCharacteristics addVampirism(int vampirism) {
        this.vampirism += vampirism;
        return this;
    }

    public WarriorCharacteristics addChangeOfDoubleDamage(int changeOfDoubleDamage) {
        this.criticalDamageChange += changeOfDoubleDamage;
        return this;
    }

    public WarriorCharacteristics addChangeOfStun(int changeOfStun) {
        this.changeOfStun += changeOfStun;
        return this;
    }

    public WarriorCharacteristics addVelocity(int velocity) {
        this.velocity += velocity;
        return this;
    }

    public WarriorCharacteristics addCriticalDamageChange(int criticalDamageChange) {
        this.criticalDamageChange += criticalDamageChange;
        return this;
    }

    public WarriorCharacteristics addMultiplierCriticalDamage(int multiplierCriticalDamage) {
        this.criticalDamageMultiplier += multiplierCriticalDamage;
        return this;
    }

    public WarriorCharacteristics addActivatedDefensePercent(int activatedDefensePercent) {
        this.activatedDefensePercent += activatedDefensePercent;
        return this;
    }


    public AttackType getAttackType() {
        return attackType;
    }

    public Target getTarget() {
        return target;
    }
}
