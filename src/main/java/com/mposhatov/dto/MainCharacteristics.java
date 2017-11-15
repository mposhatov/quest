package com.mposhatov.dto;

public class MainCharacteristics {

    protected long mana;
    protected long spellPower;
    protected long attack;
    protected long physicalDefense;
    protected long magicDefense;

    public MainCharacteristics() {
    }

    public MainCharacteristics(long mana, long spellPower, long attack, long physicalDefense, long magicDefense) {
        this.mana = mana;
        this.spellPower = spellPower;
        this.attack = attack;
        this.physicalDefense = physicalDefense;
        this.magicDefense = magicDefense;
    }

    public long getMana() {
        return mana;
    }

    public void setMana(long mana) {
        this.mana = mana;
    }

    public long getSpellPower() {
        return spellPower;
    }

    public void setSpellPower(long spellPower) {
        this.spellPower = spellPower;
    }

    public long getAttack() {
        return attack;
    }

    public void setAttack(long attack) {
        this.attack = attack;
    }

    public long getPhysicalDefense() {
        return physicalDefense;
    }

    public void setPhysicalDefense(long physicalDefense) {
        this.physicalDefense = physicalDefense;
    }

    public long getMagicDefense() {
        return magicDefense;
    }

    public void setMagicDefense(long magicDefense) {
        this.magicDefense = magicDefense;
    }
}
