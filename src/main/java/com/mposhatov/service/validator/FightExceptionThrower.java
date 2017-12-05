package com.mposhatov.service.validator;

import com.mposhatov.dto.*;
import com.mposhatov.exception.ClientException;
import com.mposhatov.exception.FightException;
import com.mposhatov.holder.ActiveGame;
import org.springframework.stereotype.Component;

@Component
public class FightExceptionThrower {

    public void throwIfExpectedAnotherClient(Warrior warrior, Long clientId) throws FightException.ExpectedAnotherClient {

        if (warrior.getHero().getClient().getId() != clientId) {
            throw new FightException.ExpectedAnotherClient(clientId);
        }

    }

    public void throwIfWrongTarget(ActiveGame activeGame, Target target, Warrior castingWarrior, Warrior targetWarrior) throws FightException.WrongTarget, ClientException.HasNotActiveGame {

        if (!isPossibleCast(activeGame, target, castingWarrior, targetWarrior)) {
            throw new FightException.WrongTarget(target.getTitle());
        }

    }

    public void throwIfWrongTarget(ActiveGame activeGame, Target target, Warrior castingWarrior, Integer position, Boolean isMyPosition) throws FightException.WrongTarget, ClientException.HasNotActiveGame {

        if (!isPossibleCast(activeGame, target, castingWarrior, position, isMyPosition)) {
            throw new FightException.WrongTarget(target.getTitle());
        }

    }

    public void throwIfNotEnoughMana(Warrior warrior, Integer expectedMana) throws FightException.NotEnoughMana {

        if (warrior.getWarriorCharacteristics().getMana() < expectedMana) {
            throw new FightException.NotEnoughMana(expectedMana, warrior.getWarriorCharacteristics().getMana());
        }

    }

    public void throwIfWarriorDoesNotContainSpellAttack(Warrior warrior, SpellAttack spellAttack) throws FightException.DoesNotContainSpellAttack {

        if (!warrior.getSpellAttacks().contains(spellAttack)) {
            throw new FightException.DoesNotContainSpellAttack(spellAttack.getId());
        }

    }

    public void throwIfWarriorDoesNotContainSpellHeal(Warrior warrior, SpellHeal spellHeal) throws FightException.DoesNotContainSpellHeal {

        if (!warrior.getSpellHeals().contains(spellHeal)) {
            throw new FightException.DoesNotContainSpellHeal(spellHeal.getId());
        }

    }

    public void throwIfWarriorDoesNotContainSpellExhortation(Warrior warrior, SpellExhortation spellExhortation) throws FightException.DoesNotContainSpellExhortain {

        if (!warrior.getSpellExhortations().contains(spellExhortation)) {
            throw new FightException.DoesNotContainSpellExhortain(spellExhortation.getId());
        }

    }

    public void throwIfWarriorDoesNotContainSpellPassive(Warrior warrior, SpellPassive spellPassive) throws FightException.DoesNotContainSpellPassive {

        if (!warrior.getSpellPassives().contains(spellPassive)) {
            throw new FightException.DoesNotContainSpellPassive(spellPassive.getId());
        }

    }


    private boolean isPossibleCast(ActiveGame activeGame, Target target, Warrior castingWarrior, Warrior targetWarrior) throws ClientException.HasNotActiveGame {

        boolean access = false;

        if (target.getName().equals(com.mposhatov.entity.Target.ENEMY_ALL_BUSY.name())) {
            if (targetWarrior.getHero().getClient().getId() != castingWarrior.getHero().getClient().getId()) {
                access = true;
            }
        } else if (target.getName().equals(com.mposhatov.entity.Target.ENEMY_MELEE_BUSY.name())) {
            if ((warriorIsFirstRow(castingWarrior) && warriorIsFirstRow(targetWarrior))
                    || (!warriorIsFirstRow(castingWarrior) && !warriorIsFirstRow(targetWarrior) && activeGame.isFirstRowFree(targetWarrior.getHero().getClient().getId()) && activeGame.isFirstRowFree(castingWarrior.getHero().getClient().getId()))
                    || (warriorIsFirstRow(castingWarrior) && !warriorIsFirstRow(targetWarrior) && activeGame.isFirstRowFree(targetWarrior.getHero().getClient().getId()))
                    || (!warriorIsFirstRow(castingWarrior) && warriorIsFirstRow(targetWarrior) && activeGame.isFirstRowFree(castingWarrior.getHero().getClient().getId()))) {
                access = true;
            }
        } else if (target.getName().equals(com.mposhatov.entity.Target.ALLIES_ALL_BUSY.name())) {
            if (targetWarrior.getHero().getClient().getId() == castingWarrior.getHero().getClient().getId()) {
                access = true;
            }
        }

        return access;
    }

    private boolean isPossibleCast(ActiveGame activeGame, Target target, Warrior castingWarrior, Integer position, Boolean isMyPosition) throws ClientException.HasNotActiveGame {

        boolean access = false;

        if (target.getName().equals(com.mposhatov.entity.Target.ALLIES_ALL_FREE.name())) {
            if (isMyPosition && activeGame.isColumnFree(castingWarrior.getHero().getClient().getId(), position)) {
                access = true;
            }
        }

        return access;
    }

    private boolean warriorIsFirstRow(Warrior warrior) {
        return warrior.getPosition() >= 1 && warrior.getPosition() <= 7;
    }

}
