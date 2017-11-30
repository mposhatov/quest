package com.mposhatov.dto;

import java.util.ArrayList;
import java.util.List;

public class SpellExhortation extends Spell {

    private HierarchyWarrior hierarchyWarrior;
    private SpellExhortation parentSpellExhortation;
    private List<SpellExhortation> childrenSpellExhortations = new ArrayList<>();

    public SpellExhortation() {

    }

    public SpellExhortation(Long id, String name, String description, String pictureName, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer requirementSpellPower, HierarchyWarrior hierarchyWarrior, SpellExhortation parentSpellExhortation, List<SpellExhortation> childrenSpellExhortations) {
        super(id, name, description, pictureName, mana, purchaseCostGoldCoins, purchaseCostDiamonds, updateCostGoldCoins, updateCostDiamonds, requirementHeroLevel, requirementSpellPower);
        this.hierarchyWarrior = hierarchyWarrior;
        this.parentSpellExhortation = parentSpellExhortation;
        this.childrenSpellExhortations = childrenSpellExhortations;
    }

    public HierarchyWarrior getHierarchyWarrior() {
        return hierarchyWarrior;
    }

    public SpellExhortation getParentSpellExhortation() {
        return parentSpellExhortation;
    }

    public List<SpellExhortation> getChildrenSpellExhortations() {
        return childrenSpellExhortations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpellAttack that = (SpellAttack) o;

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
