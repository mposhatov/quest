package com.mposhatov.dto;

public abstract class MainCharacteristics {

    protected int mana;
    protected int spellPower;
    protected int attack;
    protected int physicalDefense;
    protected int magicDefense;

    public MainCharacteristics() {
    }

    public MainCharacteristics(int mana, int spellPower, int attack, int physicalDefense, int magicDefense) {
        this.mana = mana;
        this.spellPower = spellPower;
        this.attack = attack;
        this.physicalDefense = physicalDefense;
        this.magicDefense = magicDefense;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getSpellPower() {
        return spellPower;
    }

    public void setSpellPower(int spellPower) {
        this.spellPower = spellPower;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getPhysicalDefense() {
        return physicalDefense;
    }

    public void setPhysicalDefense(int physicalDefense) {
        this.physicalDefense = physicalDefense;
    }

    public int getMagicDefense() {
        return magicDefense;
    }

    public void setMagicDefense(int magicDefense) {
        this.magicDefense = magicDefense;
    }
}
