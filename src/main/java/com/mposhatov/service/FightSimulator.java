package com.mposhatov.service;

import com.mposhatov.dto.WarriorCharacteristics;
import com.mposhatov.util.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FightSimulator {

    @Autowired
    private AttackSimulator attackSimulator;

    @Autowired
    private DefendSimulator defendSimulator;

    public void directionAttack(WarriorCharacteristics attackWarrior, WarriorCharacteristics defendWarrior) {

        final long damage = attackSimulator.generateDamage(attackWarrior);

        final long takingDamage = defendSimulator
                .generateTakingDamage(defendWarrior, damage, attackWarrior.getAttackType());

        defendWarrior.minusHealth(takingDamage > defendWarrior.getHealth() ? defendWarrior.getHealth() : takingDamage);

        final long vampirismHealth = Calculator.calculatePercentageOf(attackWarrior.getVampirism(), takingDamage);

        attackWarrior.addHealth(vampirismHealth);
    }
}
