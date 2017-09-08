package com.mposhatov.entity;

import javax.persistence.*;

@MappedSuperclass
public class MainCharacteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "ATTACK", nullable = false)
    protected long attack;

    @Column(name = "PHYSICAL_DEFENSE", nullable = false)
    protected long physicalDefense;

    @Column(name = "MAGIC_DEFENSE", nullable = false)
    protected long magicDefense;

    @Column(name = "SPELL_POWER", nullable = false)
    protected long spellPower;

    @Column(name = "MANA", nullable = false)
    protected long mana;

    protected MainCharacteristics() {
    }

    public MainCharacteristics(long attack, long physicalDefense, long magicDefense) {
        this.attack = attack;
        this.physicalDefense = physicalDefense;
        this.magicDefense = magicDefense;
    }

    protected MainCharacteristics addAttack(long attack) {
        this.attack += attack;
        return this;
    }

    protected MainCharacteristics minusAttack(long attack) {
        this.attack -= attack;
        return this;
    }

    protected MainCharacteristics addPhysicalDefense(long physicalDefense) {
        this.physicalDefense += physicalDefense;
        return this;
    }

    protected MainCharacteristics minusPhysicalDefense(long physicalDefense) {
        this.physicalDefense -= physicalDefense;
        return this;
    }

    protected MainCharacteristics addMagicDefense(long magicDefense) {
        this.magicDefense += magicDefense;
        return this;
    }

    protected MainCharacteristics minusMagicDefense(long magicDefense) {
        this.magicDefense -= magicDefense;
        return this;
    }

    protected MainCharacteristics addSpellPower(long spellPower) {
        this.spellPower += spellPower;
        return this;
    }

    protected MainCharacteristics minusSpellPower(long spellPower) {
        this.spellPower -= spellPower;
        return this;
    }

    protected MainCharacteristics addManaByCharacteristic() {
        this.mana += 1;
        return this;
    }

    protected MainCharacteristics addMana(long mana) {
        this.mana += mana;
        return this;
    }

    protected MainCharacteristics minusMana(long mana) {
        this.mana -= mana;
        return this;
    }

    public Long getId() {
        return id;
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

    public long getSpellPower() {
        return spellPower;
    }

    public long getMana() {
        return mana;
    }
}
