package com.mposhatov.service;

import com.mposhatov.dto.Warrior;
import com.mposhatov.dto.WarriorCharacteristics;
import com.mposhatov.util.Calculator;
import com.mposhatov.util.ProbabilitySimulator;
import org.springframework.stereotype.Service;

@Service
public class AttackSimulator {

    public long generateDamage(Warrior warrior) {

        final WarriorCharacteristics warriorCharacteristics = warrior.getWarriorCharacteristics();

        long damage = 0;

        damage += warriorCharacteristics.getAttack();

        damage += Calculator.calculatePercentageOf(warriorCharacteristics.getAdditionalDamagePercent(), damage);

        if (ProbabilitySimulator.isLucky(warriorCharacteristics.getCriticalDamageChange())) {
            damage *= warriorCharacteristics.getMultiplierCriticalDamage();
        }

        return damage;
    }

}
