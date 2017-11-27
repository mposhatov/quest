package com.mposhatov.dto;

import java.util.ArrayList;
import java.util.List;

public class SpellAttack extends Spell {
    private Integer damage;
    private Integer damageBySpellPower;
    private SpellAttack parentSpellAttack;
    private List<SpellAttack> childrenSpellAttacks = new ArrayList<>();

    public SpellAttack() {
    }

    public SpellAttack(Long id, String name, String description, String pictureName, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer damage, Integer damageBySpellPower, SpellAttack parentSpellAttack, List<SpellAttack> childrenSpellAttacks) {
        super(id, name, description, pictureName, mana, purchaseCostGoldCoins, purchaseCostDiamonds, updateCostGoldCoins, updateCostDiamonds, requirementHeroLevel);
        this.damage = damage;
        this.damageBySpellPower = damageBySpellPower;
        this.parentSpellAttack = parentSpellAttack;
        this.childrenSpellAttacks = childrenSpellAttacks;
    }

    public Integer getDamage() {
        return damage;
    }

    public Integer getDamageBySpellPower() {
        return damageBySpellPower;
    }

    public SpellAttack getParentSpellAttack() {
        return parentSpellAttack;
    }

    public List<SpellAttack> getChildrenSpellAttacks() {
        return childrenSpellAttacks;
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
