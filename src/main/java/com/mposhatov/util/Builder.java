package com.mposhatov.util;

import com.mposhatov.dto.*;
import com.mposhatov.holder.ActiveGame;

public class Builder {

    public static Warrior buildWarrior(ActiveGame activeGame, HierarchyWarrior hierarchyWarrior, Integer position, Hero hero) {
        return new Warrior(activeGame.generateWarriorId(),
                hierarchyWarrior.getName(),
                hierarchyWarrior.getPictureName(),
                hierarchyWarrior.getLevel(),
                hierarchyWarrior.getKilledExperience(),
                hierarchyWarrior.getImprovementExperience(),
                true,
                position,
                hero,
                hierarchyWarrior.getWarriorCharacteristics(),
                0,
                hierarchyWarrior.getSpellAttacks(),
                hierarchyWarrior.getSpellHeals(),
                hierarchyWarrior.getSpellExhortations(),
                hierarchyWarrior.getSpellPassives());
    }

    public static Effect buildEffect(SpellPassive spellPassive) {
        return new Effect(spellPassive.getId(), spellPassive.getName(), spellPassive.getDescription(),
                spellPassive.getPictureName(), spellPassive.getCharacteristics(), spellPassive.getDurationSteps());
    }

}
