package com.mposhatov.service;

import com.mposhatov.dto.SpellAttack;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.AttackType;
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

        final int damage = attackSimulator.generateDamage(attackWarrior);

        final int takingDamage =
                defendSimulator.generateTakingDamage(
                        defendWarrior, damage, attackWarrior.getWarriorCharacteristics().getAttackType());

        makeDamage(defendWarrior, takingDamage);

        final int vampirismHealth =
                Calculator.calculatePercentageOf(attackWarrior.getWarriorCharacteristics().getVampirism(), takingDamage);

        attackWarrior.getWarriorCharacteristics().addHealth(vampirismHealth);
    }

    public void spellAttack(Warrior attackWarrior, SpellAttack spellAttack, Warrior defendWarrior) {

        final int damage =
                attackSimulator.generateDamage(spellAttack, attackWarrior.getWarriorCharacteristics().getSpellPower());

        final int takingDamage = defendSimulator.generateTakingDamage(defendWarrior, damage, AttackType.MAGICAL);

        makeDamage(defendWarrior, takingDamage);
    }

    private void makeDamage(Warrior warrior, int damage) {
        warrior.getWarriorCharacteristics().minusHealth(
                damage > warrior.getWarriorCharacteristics().getHealth() ?
                        warrior.getWarriorCharacteristics().getHealth() : damage);
    }
}
