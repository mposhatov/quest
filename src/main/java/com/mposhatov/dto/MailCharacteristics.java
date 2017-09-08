package com.mposhatov.dto;

public class MailCharacteristics {

    private long mana;
    private long spellPower;

    private long attack;
    private long physicalDefense;
    private long magicDefense;


    public MailCharacteristics(long mana, long spellPower, long attack, long physicalDefense, long magicDefense) {
        this.mana = mana;
        this.spellPower = spellPower;
        this.attack = attack;
        this.physicalDefense = physicalDefense;
        this.magicDefense = magicDefense;
    }

    public long getMana() {
        return mana;
    }

    public long getSpellPower() {
        return spellPower;
    }

    public long getAttack() {
        return attack;
    }

    public long getPhysicalDefense() {
        return physicalDefense;
    }

    public long getMagicDefense() {
        return magicDefense;
    }
}
