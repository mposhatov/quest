package com.mposhatov.dto;

public class WarriorCharacteristics extends MailCharacteristics {

    private long health;
    private long minDamage;
    private long maxDamage;

    private long velocity;

    private long probableOfEvasion;
    private long blockPercent;
    private long additionalDamagePercent;
    private long vampirism;
    private long changeOfDoubleDamage;
    private long changeOfStun;

    public WarriorCharacteristics(long health, long mana, long spellPower, long attack, long physicalDefense, long magicDefense, long minDamage, long maxDamage, long velocity, long probableOfEvasion, long blockPercent, long additionalDamagePercent, long vampirism, long changeOfDoubleDamage, long changeOfStun) {
        super(mana, spellPower, attack, physicalDefense, magicDefense);
        this.health = health;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.velocity = velocity;
        this.probableOfEvasion = probableOfEvasion;
        this.blockPercent = blockPercent;
        this.additionalDamagePercent = additionalDamagePercent;
        this.vampirism = vampirism;
        this.changeOfDoubleDamage = changeOfDoubleDamage;
        this.changeOfStun = changeOfStun;
    }

    public long getHealth() {
        return health;
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

    public long getVelocity() {
        return velocity;
    }
}
