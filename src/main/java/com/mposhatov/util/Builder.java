package com.mposhatov.util;

import com.mposhatov.dto.Hero;
import com.mposhatov.dto.HierarchyWarrior;
import com.mposhatov.dto.Warrior;
import com.mposhatov.holder.ActiveGame;

public class Builder {

    public static Warrior buildWarrior(ActiveGame activeGame, HierarchyWarrior hierarchyWarrior, Integer position, Hero hero) {
        return new Warrior(activeGame.generetaWarriorId(),
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
                null);
    }

}
