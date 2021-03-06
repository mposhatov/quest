package com.mposhatov.service;

import com.mposhatov.dto.Warrior;
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

    public int generateTakingDamage(Warrior warrior, int damage, AttackType attackType) {

        int takenDamage = damage;

        final WarriorCharacteristics warriorCharacteristics = warrior.getWarriorCharacteristics();

        //if evasion of attack
        if (ProbabilitySimulator.isLucky(warriorCharacteristics.getProbableOfEvasion())) {
            takenDamage = 0;
        } else {
            if (attackType.equals(AttackType.PHYSICAL)) {

                takenDamage -= Calculator.calculatePercentageOf(warriorCharacteristics.getPhysicalBlockPercent(), damage);

                takenDamage -= warriorCharacteristics.getPhysicalDefense() * damageByDefence;

            } else if (attackType.equals(AttackType.MAGICAL)) {

                takenDamage -= damage * warriorCharacteristics.getMagicalBlockPercent();

                takenDamage -= warriorCharacteristics.getMagicDefense() * damageByDefence;

            }
        }

        return takenDamage < 1 ? 1 : takenDamage;
    }

    public Warrior activateDefaultDefense(Warrior warrior) {

        final WarriorCharacteristics warriorCharacteristics = warrior.getWarriorCharacteristics();

        final int defense = Calculator.calculatePercentageOf(warriorCharacteristics.getActivatedDefensePercent(), warriorCharacteristics.getPhysicalDefense());

        warriorCharacteristics.addPhysicalDefense(defense);

        return warrior;
    }


}
