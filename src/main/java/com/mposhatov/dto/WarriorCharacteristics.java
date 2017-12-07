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

    public WarriorCharacteristics plusHealth(int health) {
        this.health += health;
        if (this.health < 0) {
            this.health = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusHealth(int health) {
        this.health -= health;
        if (this.health < 0) {
            this.health = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusPhysicalDefense(int physicalDefense) {
        this.physicalDefense += physicalDefense;
        if (this.physicalDefense < 0) {
            this.physicalDefense = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusPhysicalDefense(int physicalDefense) {
        this.physicalDefense -= physicalDefense;
        if (this.physicalDefense < 0) {
            this.physicalDefense = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusMana(int mana) {
        this.mana += mana;
        if (this.mana < 0) {
            this.mana = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusMana(int mana) {
        this.mana -= mana;
        if (this.mana < 0) {
            this.mana = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusAttack(int attack) {
        this.attack += attack;
        if (this.attack < 0) {
            this.attack = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusAttack(int attack) {
        this.attack -= attack;
        if (this.attack < 0) {
            this.attack = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusMagicDefense(int magicDefense) {
        this.magicDefense += magicDefense;
        if (this.magicDefense < 0) {
            this.magicDefense = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusMagicDefense(int magicDefense) {
        this.magicDefense -= magicDefense;
        if (this.magicDefense < 0) {
            this.magicDefense = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusSpellPower(int spellPower) {
        this.spellPower += spellPower;
        if (this.spellPower < 0) {
            this.spellPower = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusSpellPower(int spellPower) {
        this.spellPower -= spellPower;
        if (this.spellPower < 0) {
            this.spellPower = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusProbableOfEvasion(int probableOfEvasion) {
        this.probableOfEvasion += probableOfEvasion;
        if (this.probableOfEvasion < 0) {
            this.probableOfEvasion = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusProbableOfEvasion(int probableOfEvasion) {
        this.probableOfEvasion -= probableOfEvasion;
        if (this.probableOfEvasion < 0) {
            this.probableOfEvasion = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusPhysicalBlockPercent(int physicalBlockPercent) {
        this.physicalBlockPercent += physicalBlockPercent;
        if (this.physicalBlockPercent < 0) {
            this.physicalBlockPercent = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusPhysicalBlockPercent(int physicalBlockPercent) {
        this.physicalBlockPercent -= physicalBlockPercent;
        if (this.physicalBlockPercent < 0) {
            this.physicalBlockPercent = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusMagicalBlockPercent(int magicalBlockPercent) {
        this.magicalBlockPercent += magicalBlockPercent;
        if (this.magicalBlockPercent < 0) {
            this.magicalBlockPercent = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusMagicalBlockPercent(int magicalBlockPercent) {
        this.magicalBlockPercent -= magicalBlockPercent;
        if (this.magicalBlockPercent < 0) {
            this.magicalBlockPercent = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusAdditionalDamagePercent(int additionalDamagePercent) {
        this.additionalDamagePercent += additionalDamagePercent;
        if (this.additionalDamagePercent < 0) {
            this.additionalDamagePercent = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusAdditionalDamagePercent(int additionalDamagePercent) {
        this.additionalDamagePercent -= additionalDamagePercent;
        if (this.additionalDamagePercent < 0) {
            this.additionalDamagePercent = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusVampirism(int vampirism) {
        this.vampirism += vampirism;
        if (this.vampirism < 0) {
            this.vampirism = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusVampirism(int vampirism) {
        this.vampirism -= vampirism;
        if (this.vampirism < 0) {
            this.vampirism = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusChangeOfDoubleDamage(int changeOfDoubleDamage) {
        this.criticalDamageChange += changeOfDoubleDamage;
        if (this.criticalDamageChange < 0) {
            this.criticalDamageChange = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusChangeOfDoubleDamage(int changeOfDoubleDamage) {
        this.criticalDamageChange -= changeOfDoubleDamage;
        if (this.criticalDamageChange < 0) {
            this.criticalDamageChange = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusChangeOfStun(int changeOfStun) {
        this.changeOfStun += changeOfStun;
        if (this.changeOfStun < 0) {
            this.changeOfStun = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusChangeOfStun(int changeOfStun) {
        this.changeOfStun -= changeOfStun;
        if (this.changeOfStun < 0) {
            this.changeOfStun = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusVelocity(int velocity) {
        this.velocity += velocity;
        if (this.velocity < 0) {
            this.velocity = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusVelocity(int velocity) {
        this.velocity -= velocity;
        if (this.velocity < 0) {
            this.velocity = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusCriticalDamageChange(int criticalDamageChange) {
        this.criticalDamageChange += criticalDamageChange;
        if (this.criticalDamageChange < 0) {
            this.criticalDamageChange = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusCriticalDamageChange(int criticalDamageChange) {
        this.criticalDamageChange -= criticalDamageChange;
        if (this.criticalDamageChange < 0) {
            this.criticalDamageChange = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusMultiplierCriticalDamage(int multiplierCriticalDamage) {
        this.criticalDamageMultiplier += multiplierCriticalDamage;
        if (this.criticalDamageMultiplier < 0) {
            this.criticalDamageMultiplier = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusMultiplierCriticalDamage(int multiplierCriticalDamage) {
        this.criticalDamageMultiplier -= multiplierCriticalDamage;
        if (this.criticalDamageMultiplier < 0) {
            this.criticalDamageMultiplier = 0;
        }
        return this;
    }

    public WarriorCharacteristics plusActivatedDefensePercent(int activatedDefensePercent) {
        this.activatedDefensePercent += activatedDefensePercent;
        if (this.activatedDefensePercent < 0) {
            this.activatedDefensePercent = 0;
        }
        return this;
    }

    public WarriorCharacteristics minusActivatedDefensePercent(int activatedDefensePercent) {
        this.activatedDefensePercent -= activatedDefensePercent;
        if (this.activatedDefensePercent < 0) {
            this.activatedDefensePercent = 0;
        }
        return this;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public Target getTarget() {
        return target;
    }
}
