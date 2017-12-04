package com.mposhatov.entity;

import com.mposhatov.dto.WarriorCharacteristics;

public class CharacteristicsMerge {
    public static void mapPlusWarriorCharacteristics(WarriorCharacteristics destination, com.mposhatov.dto.QuantifiableWarriorCharacteristics source) {
        destination.addAttack(source.getAttack());
        destination.addPhysicalDefense(source.getPhysicalDefense());
        destination.addMagicDefense(source.getMagicDefense());
        destination.addSpellPower(source.getSpellPower());
        destination.addHealth(source.getHealth());
        destination.addMana(source.getMana());
        destination.addActivatedDefensePercent(source.getActivatedDefensePercent());
        destination.addVelocity(source.getVelocity());
        destination.addProbableOfEvasion(source.getProbableOfEvasion());
        destination.addPhysicalBlockPercent(source.getPhysicalBlockPercent());
        destination.addMagicalBlockPercent(source.getMagicalBlockPercent());
        destination.addAdditionalDamagePercent(source.getAdditionalDamagePercent());
        destination.addVampirism(source.getVampirism());
        destination.addChangeOfDoubleDamage(source.getCriticalDamageChange());
        destination.addChangeOfStun(source.getChangeOfStun());
    }

    public static void mapMinusWarriorCharacteristics(WarriorCharacteristics destination, com.mposhatov.dto.QuantifiableWarriorCharacteristics source) {
        destination.addAttack(-source.getAttack());
        destination.addPhysicalDefense(-source.getPhysicalDefense());
        destination.addMagicDefense(-source.getMagicDefense());
        destination.addSpellPower(-source.getSpellPower());
        destination.addHealth(-source.getHealth());
        destination.addMana(-source.getMana());
        destination.addActivatedDefensePercent(-source.getActivatedDefensePercent());
        destination.addVelocity(-source.getVelocity());
        destination.addProbableOfEvasion(-source.getProbableOfEvasion());
        destination.addPhysicalBlockPercent(-source.getPhysicalBlockPercent());
        destination.addMagicalBlockPercent(-source.getMagicalBlockPercent());
        destination.addAdditionalDamagePercent(-source.getAdditionalDamagePercent());
        destination.addVampirism(-source.getVampirism());
        destination.addChangeOfDoubleDamage(-source.getCriticalDamageChange());
        destination.addChangeOfStun(-source.getChangeOfStun());
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
