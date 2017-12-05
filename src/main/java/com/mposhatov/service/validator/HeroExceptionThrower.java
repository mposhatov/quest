package com.mposhatov.service.validator;

import com.mposhatov.entity.DbHero;
import com.mposhatov.entity.DbHierarchyWarrior;
import com.mposhatov.entity.DbInventory;
import com.mposhatov.exception.HeroException;
import org.springframework.stereotype.Component;

@Component
public class HeroExceptionThrower {

    public void throwIfHierarchyWarriorAvailable(DbHero hero, DbHierarchyWarrior hierarchyWarrior) throws HeroException.HierarchyWarriorAvailable {

        if (hero.getAvailableHierarchyWarriors().contains(hierarchyWarrior)) {
            throw new HeroException.HierarchyWarriorAvailable(hierarchyWarrior.getId());
        }

    }

    public void throwIfHierarchyWarriorNotAvailable(DbHero hero, DbHierarchyWarrior hierarchyWarrior) throws HeroException.HierarchyWarriorNotAvailable {

        if (!hero.getAvailableHierarchyWarriors().contains(hierarchyWarrior)) {
            throw new HeroException.HierarchyWarriorNotAvailable(hierarchyWarrior.getId());
        }

    }

    public void throwIfNotEnoughLevelToHierarchyWarrior(Long clientId, DbHero hero, DbHierarchyWarrior hierarchyWarrior) throws HeroException.NotEnoughLevelToHierarchyWarrior {

        if (hero.getLevel() < hierarchyWarrior.getRequirementHeroLevel()) {
            throw new HeroException.NotEnoughLevelToHierarchyWarrior(clientId, hierarchyWarrior.getId(), hierarchyWarrior.getRequirementHeroLevel());
        }

    }

    public void throwIfNotEnoughResourcesToUpdateHierarchyWarrior(DbHero hero, DbHierarchyWarrior hierarchyWarrior) throws HeroException.NotEnoughResourcesToUpgradeHierarchyWarrior {

        final DbInventory inventory = hero.getInventory();

        if (inventory.getGoldCoins() < hierarchyWarrior.getUpdateCostGoldCoins()
                || inventory.getDiamonds() < hierarchyWarrior.getUpdateCostDiamonds()) {
            throw new HeroException.NotEnoughResourcesToUpgradeHierarchyWarrior(hero.getClientId(), hierarchyWarrior.getId());
        }
    }

    public void throwIfNotEnoughResourcesToBuyHierarchyWarrior(DbHero hero, DbHierarchyWarrior hierarchyWarrior) throws HeroException.NotEnoughResourcesToBuyHierarchyWarrior {

        final DbInventory inventory = hero.getInventory();

        if (inventory.getGoldCoins() < hierarchyWarrior.getPurchaseCostGoldCoins()
                || inventory.getDiamonds() < hierarchyWarrior.getPurchaseCostDiamonds()) {
            throw new HeroException.NotEnoughResourcesToBuyHierarchyWarrior(hero.getClientId(), hierarchyWarrior.getId());
        }
    }

}
