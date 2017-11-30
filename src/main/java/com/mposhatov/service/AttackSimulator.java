package com.mposhatov.service;

import com.mposhatov.dto.SpellAttack;
import com.mposhatov.dto.Warrior;
import com.mposhatov.dto.WarriorCharacteristics;
import com.mposhatov.util.Calculator;
import com.mposhatov.util.ProbabilitySimulator;
import org.springframework.stereotype.Service;

@Service
public class AttackSimulator {

    public int generateDamage(Warrior warrior) {

        final WarriorCharacteristics warriorCharacteristics = warrior.getWarriorCharacteristics();

        int damage = warriorCharacteristics.getAttack();

        damage += Calculator.calculatePercentageOf(warriorCharacteristics.getAdditionalDamagePercent(), damage);

        if (ProbabilitySimulator.isLucky(warriorCharacteristics.getCriticalDamageChange())) {
            damage *= warriorCharacteristics.getMultiplierCriticalDamage();
        }

        return damage;
    }

    public int generateDamage(SpellAttack spellAttack, Integer spellPower) {

        int damage = spellAttack.getDamage();

        damage += spellPower != null && spellPower != 0 ? spellAttack.getDamageBySpellPower() * spellPower : 0;

        return damage;
    }

}
