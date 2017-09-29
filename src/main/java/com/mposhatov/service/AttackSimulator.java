package com.mposhatov.service;

import com.mposhatov.dto.WarriorCharacteristics;
import com.mposhatov.util.Calculator;
import com.mposhatov.util.ProbabilitySimulator;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AttackSimulator {

    @Value("${game.damageByAttack}")
    private long damageByAttack;

    public long generateDamage(WarriorCharacteristics warrior) {
        long damage = 0;

        damage += new RandomDataGenerator().nextLong(warrior.getMinDamage(), warrior.getMaxDamage());

        damage += warrior.getAttack() * damageByAttack;

        damage += Calculator.calculatePercentageOf(warrior.getAdditionalDamagePercent(), damage);

        if (ProbabilitySimulator.isLucky(warrior.getCriticalDamageChange())) {
            damage *= warrior.getMultiplierCriticalDamage();
        }

        return damage;
    }

}
