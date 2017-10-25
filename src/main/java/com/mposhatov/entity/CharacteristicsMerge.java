package com.mposhatov.entity;

public class CharacteristicsMerge {
    static void mapPlusWarriorCharacteristics(DbWarriorCharacteristics destination, MainWarriorCharacteristics source) {
        destination.addAttack(source.getAttack());
        destination.addPhysicalDefense(source.getPhysicalDefense());

        destination.addSpellPower(source.getSpellPower());
        destination.addHealth(source.getHealth());
        destination.addMana(source.getMana());

        destination.addDamage(source.getMinDamage(), source.getMaxDamage());

        destination.addVelocity(source.getVelocity());

        destination.addProbableOfEvasion(source.getProbableOfEvasion());
        destination.addPhysicalBlockPercent(source.getPhysicalBlockPercent());
        destination.addMagicalBlockPercent(source.getMagicalBlockPercent());
        destination.addAdditionalDamagePercent(source.getAdditionalDamagePercent());
        destination.addVampirism(source.getVampirism());
        destination.addChangeOfDoubleDamage(source.getCriticalDamageChange());
        destination.addChangeOfStun(source.getChangeOfStun());
    }

    static void mapMinusWarriorCharacteristics(DbWarriorCharacteristics destination, MainWarriorCharacteristics source) {
        destination.addAttack(-source.getAttack());
        destination.addPhysicalDefense(-source.getPhysicalDefense());

        destination.addSpellPower(-source.getSpellPower());
        destination.addHealth(-source.getHealth());
        destination.addMana(-source.getMana());

        destination.addDamage(-source.getMinDamage(), source.getMaxDamage());

        destination.addVelocity(-source.getVelocity());

        destination.addProbableOfEvasion(-source.getProbableOfEvasion());
        destination.addPhysicalBlockPercent(-source.getPhysicalBlockPercent());
        destination.addMagicalBlockPercent(-source.getMagicalBlockPercent());
        destination.addAdditionalDamagePercent(-source.getAdditionalDamagePercent());
        destination.addVampirism(-source.getVampirism());
        destination.addChangeOfDoubleDamage(-source.getCriticalDamageChange());
        destination.addChangeOfStun(-source.getChangeOfStun());
    }

    static void mapPlusHeroCharacteristics(DbHeroCharacteristics destination, DbHeroCharacteristics source) {
        destination.addAttack(source.getAttack());
        destination.addPhysicalDefense(source.getPhysicalDefense());

        destination.addSpellPower(source.getSpellPower());
        destination.addMana(source.getMana());
    }

    static void mapMinusHeroCharacteristics(DbHeroCharacteristics destination, DbHeroCharacteristics source) {
        destination.addAttack(-source.getAttack());
        destination.addPhysicalDefense(-source.getPhysicalDefense());

        destination.addSpellPower(-source.getSpellPower());
        destination.addMana(-source.getMana());
    }
}
