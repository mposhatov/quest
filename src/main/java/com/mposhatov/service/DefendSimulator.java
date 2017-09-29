package com.mposhatov.service;

import com.mposhatov.dto.WarriorCharacteristics;
import com.mposhatov.entity.AttackType;
import com.mposhatov.util.Calculator;
import com.mposhatov.util.ProbabilitySimulator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefendSimulator {

    @Value("${game.damageByDefence}")
    private long damageByDefence;

    public long generateTakingDamage(WarriorCharacteristics warrior, long damage, AttackType attackType) {
        long takenDamage = damage;

        //if evasion of attack
        if (ProbabilitySimulator.isLucky(warrior.getProbableOfEvasion())) {
            takenDamage = 0;
        } else {
            if (attackType.equals(AttackType.PHYSICAL)) {

                takenDamage -= Calculator.calculatePercentageOf(warrior.getPhysicalBlockPercent(), damage);

                takenDamage -= warrior.getPhysicalDefense() * damageByDefence;

            } else if (attackType.equals(AttackType.MAGICAL)) {

                takenDamage -= damage * warrior.getMagicalBlockPercent();

                takenDamage -= warrior.getMagicDefense() * damageByDefence;

            }
        }

        takenDamage = takenDamage < 1 ? 1 : takenDamage;

        return takenDamage;
    }

}
