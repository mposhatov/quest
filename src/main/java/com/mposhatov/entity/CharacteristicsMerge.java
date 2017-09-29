package com.mposhatov.entity;

public class CharacteristicsMerge {
    static void mapPlusWarriorCharacteristics(DbWarriorCharacteristics destination, DbWarriorCharacteristics source) {
        destination.addAttack(source.getAttack());
        destination.addPhysicalDefense(source.getPhysicalDefense());

        destination.addSpellPower(source.getSpellPower());
        destination.addHealth(source.getHealth());
        destination.addMana(source.getMana());

        destination.addDamage(source.getMinDamage(), source.getMaxDamage());

        destination.addVelocity(source.getVelocity());

        destination.addProbableOfEvasion(source.getProbableOfEvasion());
        destination.addBlockPercent(source.getBlockPercent());
        destination.addAdditionalDamagePercent(source.getAdditionalDamagePercent());
        destination.addVampirism(source.getVampirism());
        destination.addChangeOfDoubleDamage(source.getCriticalDamageChange());
        destination.addChangeOfStun(source.getChangeOfStun());
    }

    static void mapMinusWarriorCharacteristics(DbWarriorCharacteristics destination, DbWarriorCharacteristics source) {
        destination.minusAttack(source.getAttack());
        destination.minusPhysicalDefense(source.getPhysicalDefense());

        destination.minusSpellPower(source.getSpellPower());
        destination.minusHealth(source.getHealth());
        destination.minusMana(source.getMana());

        destination.minusDamage(source.getMinDamage(), source.getMaxDamage());

        destination.minusVelocity(source.getVelocity());

        destination.minusProbableOfEvasion(source.getProbableOfEvasion());
        destination.minusBlockPercent(source.getBlockPercent());
        destination.minusAdditionalDamagePercent(source.getAdditionalDamagePercent());
        destination.minusVampirism(source.getVampirism());
        destination.minusChangeOfDoubleDamage(source.getCriticalDamageChange());
        destination.minusChangeOfStun(source.getChangeOfStun());
    }

    static void mapPlusHeroCharacteristics(DbHeroCharacteristics destination, DbHeroCharacteristics source) {
        destination.addAttack(source.getAttack());
        destination.addPhysicalDefense(source.getPhysicalDefense());

        destination.addSpellPower(source.getSpellPower());
        destination.addMana(source.getMana());
    }

    static void mapMinusHeroCharacteristics(DbHeroCharacteristics destination, DbHeroCharacteristics source) {
        destination.minusAttack(source.getAttack());
        destination.minusPhysicalDefense(source.getPhysicalDefense());

        destination.minusSpellPower(source.getSpellPower());
        destination.minusMana(source.getMana());
    }
}
