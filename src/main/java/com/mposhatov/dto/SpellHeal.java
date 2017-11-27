package com.mposhatov.dto;

import java.util.ArrayList;
import java.util.List;

public class SpellHeal extends Spell {
    private Integer health;
    private Integer healthBySpellPower;
    private SpellHeal parentSpellHeal;
    private List<SpellHeal> childrenSpellHeals = new ArrayList<>();

    public SpellHeal() {
    }

    public SpellHeal(Long id, String name, String description, String pictureName, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer health, Integer healthBySpellPower, SpellHeal parentSpellHeal, List<SpellHeal> childrenSpellHeals) {
        super(id, name, description, pictureName, mana, purchaseCostGoldCoins, purchaseCostDiamonds, updateCostGoldCoins, updateCostDiamonds, requirementHeroLevel);
        this.health = health;
        this.healthBySpellPower = healthBySpellPower;
        this.parentSpellHeal = parentSpellHeal;
        this.childrenSpellHeals = childrenSpellHeals;
    }

    public Integer getHealth() {
        return health;
    }

    public Integer getHealthBySpellPower() {
        return healthBySpellPower;
    }

    public SpellHeal getParentSpellHeal() {
        return parentSpellHeal;
    }

    public List<SpellHeal> getChildrenSpellHeals() {
        return childrenSpellHeals;
    }
}
