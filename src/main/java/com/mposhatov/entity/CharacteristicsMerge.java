package com.mposhatov.entity;

import com.mposhatov.dto.WarriorCharacteristics;

public class CharacteristicsMerge {
    public static void mapPlusWarriorCharacteristics(WarriorCharacteristics destination, com.mposhatov.dto.QuantifiableWarriorCharacteristics source) {
        destination.plusAttack(source.getAttack());
        destination.plusPhysicalDefense(source.getPhysicalDefense());
        destination.plusMagicDefense(source.getMagicDefense());
        destination.plusSpellPower(source.getSpellPower());
        destination.plusHealth(source.getHealth());
        destination.plusMana(source.getMana());
        destination.plusActivatedDefensePercent(source.getActivatedDefensePercent());
        destination.plusVelocity(source.getVelocity());
        destination.plusProbableOfEvasion(source.getProbableOfEvasion());
        destination.plusPhysicalBlockPercent(source.getPhysicalBlockPercent());
        destination.plusMagicalBlockPercent(source.getMagicalBlockPercent());
        destination.plusAdditionalDamagePercent(source.getAdditionalDamagePercent());
        destination.plusVampirism(source.getVampirism());
        destination.plusChangeOfDoubleDamage(source.getCriticalDamageChange());
        destination.plusChangeOfStun(source.getChangeOfStun());
    }

    public static void mapMinusWarriorCharacteristics(WarriorCharacteristics destination, com.mposhatov.dto.QuantifiableWarriorCharacteristics source) {
        destination.minusAttack(source.getAttack());
        destination.minusPhysicalDefense(source.getPhysicalDefense());
        destination.minusMagicDefense(source.getMagicDefense());
        destination.minusSpellPower(source.getSpellPower());
        destination.minusHealth(source.getHealth());
        destination.minusMana(source.getMana());
        destination.minusActivatedDefensePercent(source.getActivatedDefensePercent());
        destination.minusVelocity(source.getVelocity());
        destination.minusProbableOfEvasion(source.getProbableOfEvasion());
        destination.minusPhysicalBlockPercent(source.getPhysicalBlockPercent());
        destination.minusMagicalBlockPercent(source.getMagicalBlockPercent());
        destination.minusAdditionalDamagePercent(source.getAdditionalDamagePercent());
        destination.minusVampirism(source.getVampirism());
        destination.minusChangeOfDoubleDamage(source.getCriticalDamageChange());
        destination.minusChangeOfStun(source.getChangeOfStun());
    }

    public static void mapPlusHeroCharacteristics(DbHeroCharacteristics destination, DbHeroCharacteristics source) {
        destination.addAttack(source.getAttack());
        destination.addPhysicalDefense(source.getPhysicalDefense());

        destination.addSpellPower(source.getSpellPower());
        destination.addMana(source.getMana());
    }

    public static void mapMinusHeroCharacteristics(DbHeroCharacteristics destination, DbHeroCharacteristics source) {
        destination.addAttack(-source.getAttack());
        destination.addPhysicalDefense(-source.getPhysicalDefense());

        destination.addSpellPower(-source.getSpellPower());
        destination.addMana(-source.getMana());
    }

    public static void mapPlusHeroPoints(DbHero destination, DbAdditionalHeroPoint source) {
        destination.addAvailableCharacteristics(source.getAvailableCharacteristics());
        destination.addAvailableSkills(source.getAvailableSkills());
        destination.addAvailableSlots(source.getAvailableSlots());
    }

    public static void mapMinusHeroPoints(DbHero destination, DbAdditionalHeroPoint source) {
        destination.addAvailableCharacteristics(-source.getAvailableCharacteristics());
        destination.addAvailableSkills(-source.getAvailableSkills());
        destination.addAvailableSlots(-source.getAvailableSlots());
    }
}
