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

    public SpellHeal(Long id, String name, String description, String pictureName, Target target, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer requirementSpellPower, Integer health, Integer healthBySpellPower, SpellHeal parentSpellHeal, List<SpellHeal> childrenSpellHeals) {
        super(id, name, description, pictureName, target, mana, purchaseCostGoldCoins, purchaseCostDiamonds, updateCostGoldCoins, updateCostDiamonds, requirementHeroLevel, requirementSpellPower);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpellHeal that = (SpellHeal) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
