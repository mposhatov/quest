package com.mposhatov.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AllWarriorCharacteristics extends QuantifiableWarriorCharacteristics {

    @Convert(converter = AttackTypeConverter.class)
    @Column(name = "ATTACK_TYPE", nullable = false)
    protected AttackType attackType;

    @Convert(converter = TargetConverter.class)
    @Column(name = "TARGET", nullable = false)
    protected Target target;

    protected AllWarriorCharacteristics() {
    }

    public AllWarriorCharacteristics(int attack, AttackType attackType, Target target, int physicalDefense, int magicDefense, int health, int velocity, int activatedDefensePercent) {
        super(attack, physicalDefense, magicDefense, health, velocity, activatedDefensePercent);
        this.attackType = attackType;
        this.target = target;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public Target getTarget() {
        return target;
    }
}
