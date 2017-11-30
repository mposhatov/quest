package com.mposhatov.entity;

import javax.persistence.*;

@MappedSuperclass
public class MainCharacteristics {

    @Column(name = "ATTACK", nullable = false)
    protected int attack;

    @Column(name = "PHYSICAL_DEFENSE", nullable = false)
    protected int physicalDefense;

    @Column(name = "MAGIC_DEFENSE", nullable = false)
    protected int magicDefense;

    @Column(name = "SPELL_POWER", nullable = false)
    protected int spellPower;

    @Column(name = "MANA", nullable = false)
    protected int mana;

    protected MainCharacteristics() {
    }

    public MainCharacteristics(int attack, int physicalDefense, int magicDefense) {
        this.attack = attack;
        this.physicalDefense = physicalDefense;
        this.magicDefense = magicDefense;
    }

    protected MainCharacteristics addAttack(int attack) {
        this.attack += attack;
        return this;
    }

    protected MainCharacteristics addPhysicalDefense(int physicalDefense) {
        this.physicalDefense += physicalDefense;
        return this;
    }

    protected MainCharacteristics addMagicDefense(int magicDefense) {
        this.magicDefense += magicDefense;
        return this;
    }

    protected MainCharacteristics addSpellPower(int spellPower) {
        this.spellPower += spellPower;
        return this;
    }

    protected MainCharacteristics addMana(int mana) {
        this.mana += mana;
        return this;
    }

    public int getAttack() {
        return attack;
    }

    public int getPhysicalDefense() {
        return physicalDefense;
    }

    public int getMagicDefense() {
        return magicDefense;
    }

    public int getSpellPower() {
        return spellPower;
    }

    public int getMana() {
        return mana;
    }
}
