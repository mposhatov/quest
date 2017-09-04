package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "CHARACTERISTICS")
public class DbCharacteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ATTACK", nullable = false)
    private long attack;

    @Column(name = "PHYSICAL_DEFENSE", nullable = false)
    private long physicalDefense;

    @Column(name = "MAGIC_DEFENSE", nullable = false)
    private long magicDefense;

    @Column(name = "SPELL_POWER", nullable = false)
    private long spellPower;

    @Column(name = "KNOWLEDGE", nullable = false)
    private long knowledge;

    @Column(name = "STRENGTH", nullable = false)
    private long strength;

    @Column(name = "HEALTH", nullable = false)
    private long health;

    @Column(name = "MANA", nullable = false)
    private long mana;

    @Column(name = "MIN_DAMAGE", nullable = false)
    private long minDamage;

    @Column(name = "MAX_DAMAGE", nullable = false)
    private long maxDamage;

    @Column(name = "PROBABILITY_OF_EVASION", nullable = false)
    private long probableOfEvasion;

    @Column(name = "BLOCK_PERCENT", nullable = false)
    private long blockPercent;

    @Column(name = "ADDITIONAL_DAMAGE_PERCENT", nullable = false)
    private long additionalDamagePercent;

    @Column(name = "VAMPIRISM", nullable = false)
    private long vampirism;

    @Column(name = "CHANGE_OF_DOUBLE_DAMAGE", nullable = false)
    private long changeOfDoubleDamage;

    @Column(name = "CHANGE_OF_STUN", nullable = false)
    private long changeOfStun;

    public DbCharacteristics() {
        addAttack(1);
        addPhysicalDefense(1);
        addMagicDefense(1);
        addSpellPower(1);
        addKnowledge(1);
        addStrength(1);
        addDamage(1, 1);
    }

    public DbCharacteristics addAttack(long attack) {
        this.attack += attack;
        return this;
    }

    public DbCharacteristics minusAttack(long attack) {
        this.attack -= attack;
        return this;
    }

    public DbCharacteristics addPhysicalDefense(long physicalDefense) {
        this.physicalDefense += physicalDefense;
        return this;
    }

    public DbCharacteristics minusPhysicalDefense(long physicalDefense) {
        this.physicalDefense -= physicalDefense;
        return this;
    }

    public DbCharacteristics addMagicDefense(long magicDefense) {
        this.magicDefense += magicDefense;
        return this;
    }

    public DbCharacteristics minusMagicDefense(long magicDefense) {
        this.magicDefense -= magicDefense;
        return this;
    }

    public DbCharacteristics addSpellPower(long spellPower) {
        this.spellPower += spellPower;
        return this;
    }

    public DbCharacteristics minusSpellPower(long spellPower) {
        this.spellPower -= spellPower;
        return this;
    }

    public DbCharacteristics addKnowledge(long knowledge) {
        this.knowledge += knowledge;
        this.mana += knowledge * 10;
        return this;
    }

    public DbCharacteristics minusKnowledge(long knowledge) {
        this.knowledge -= knowledge;
        this.mana -= knowledge * 10;
        return this;
    }

    public DbCharacteristics addStrength(long strength) {
        this.strength += strength;
        this.health += strength * 100;
        return this;
    }

    public DbCharacteristics minusStrength(long strength) {
        this.strength -= strength;
        this.health -= strength * 100;
        return this;
    }


    public DbCharacteristics addDamage(long minDamage, long maxDamage) {
        this.minDamage += minDamage;
        this.maxDamage += maxDamage;
        return this;
    }

    public DbCharacteristics minusDamage(long minDamage, long maxDamage) {
        this.minDamage -= minDamage;
        this.maxDamage -= maxDamage;
        return this;
    }
    public DbCharacteristics addProbableOfEvasion(long probableOfEvasion) {
        this.probableOfEvasion += probableOfEvasion;
        return this;
    }

    public DbCharacteristics minusProbableOfEvasion(long probableOfEvasion) {
        this.probableOfEvasion -= probableOfEvasion;
        return this;
    }

    public DbCharacteristics addBlockPercent(long blockPercent) {
        this.blockPercent += blockPercent;
        return this;
    }

    public DbCharacteristics minusBlockPercent(long blockPercent) {
        this.blockPercent -= blockPercent;
        return this;
    }

    public DbCharacteristics addAdditionalDamagePercent(long additionalDamagePercent) {
        this.additionalDamagePercent += additionalDamagePercent;
        return this;
    }

    public DbCharacteristics minusAdditionalDamagePercent(long additionalDamagePercent) {
        this.additionalDamagePercent -= additionalDamagePercent;
        return this;
    }

    public DbCharacteristics addVampirism(long vampirism) {
        this.vampirism += vampirism;
        return this;
    }

    public DbCharacteristics minusVampirism(long vampirism) {
        this.vampirism -= vampirism;
        return this;
    }

    public DbCharacteristics addChangeOfDoubleDamage(long changeOfDoubleDamage) {
        this.changeOfDoubleDamage += changeOfDoubleDamage;
        return this;
    }

    public DbCharacteristics minusChangeOfDoubleDamage(long changeOfDoubleDamage) {
        this.changeOfDoubleDamage -= changeOfDoubleDamage;
        return this;
    }

    public DbCharacteristics addChangeOfStun(long changeOfStun) {
        this.changeOfStun += changeOfStun;
        return this;
    }

    public DbCharacteristics minusChangeOfStun(long changeOfStun) {
        this.changeOfStun -= changeOfStun;
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

    public long getKnowledge() {
        return knowledge;
    }

    public long getStrength() {
        return strength;
    }

    public long getHealth() {
        return health;
    }

    public long getMana() {
        return mana;
    }

    public long getMinDamage() {
        return minDamage;
    }

    public long getMaxDamage() {
        return maxDamage;
    }

    public long getProbableOfEvasion() {
        return probableOfEvasion;
    }

    public long getBlockPercent() {
        return blockPercent;
    }

    public long getAdditionalDamagePercent() {
        return additionalDamagePercent;
    }

    public long getVampirism() {
        return vampirism;
    }

    public long getChangeOfDoubleDamage() {
        return changeOfDoubleDamage;
    }

    public long getChangeOfStun() {
        return changeOfStun;
    }
}
