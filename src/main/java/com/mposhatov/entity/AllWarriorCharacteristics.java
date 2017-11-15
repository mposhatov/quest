package com.mposhatov.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AllWarriorCharacteristics extends QuantifiableWarriorCharacteristics {

    @Convert(converter = AttackTypeConverter.class)
    @Column(name = "ATTACK_TYPE", nullable = false)
    protected AttackType attackType;

    @Convert(converter = RangeTypeConverter.class)
    @Column(name = "RANGE_TYPE", nullable = false)
    protected RangeType rangeType;

    protected AllWarriorCharacteristics() {
    }

    public AllWarriorCharacteristics(long attack, AttackType attackType, RangeType rangeType, long physicalDefense, long magicDefense, long health, int velocity, int activatedDefensePercent) {
        super(attack, physicalDefense, magicDefense, health, velocity, activatedDefensePercent);
        this.attackType = attackType;
        this.rangeType = rangeType;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public RangeType getRangeType() {
        return rangeType;
    }
}
