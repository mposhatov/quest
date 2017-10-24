package com.mposhatov.service;

import com.mposhatov.dto.Warrior;
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

    public void simpleAttack(Warrior attackWarrior, Warrior defendWarrior) {

        final WarriorCharacteristics attackWarriorCharacteristics = attackWarrior.getWarriorCharacteristics();
        final WarriorCharacteristics defendWarriorCharacteristics = defendWarrior.getWarriorCharacteristics();

        final long damage = attackSimulator.generateDamage(attackWarriorCharacteristics);

        final long takingDamage = defendSimulator
                .generateTakingDamage(defendWarrior, damage, attackWarriorCharacteristics.getAttackType());

        defendWarriorCharacteristics.minusHealth(
                takingDamage > defendWarriorCharacteristics.getHealth() ?
                        defendWarriorCharacteristics.getHealth() : takingDamage);

        final long vampirismHealth = Calculator.calculatePercentageOf(attackWarriorCharacteristics.getVampirism(), takingDamage);

        attackWarriorCharacteristics.addHealth(vampirismHealth);
    }
}
