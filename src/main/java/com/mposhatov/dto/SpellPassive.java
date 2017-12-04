package com.mposhatov.dto;

import java.util.ArrayList;
import java.util.List;

public class SpellPassive extends Spell {

    private QuantifiableWarriorCharacteristics characteristics;
    private Integer durationSteps;
    private SpellPassive parentSpellPassive;
    private List<SpellPassive> childrenSpellPassives = new ArrayList<>();

    public SpellPassive() {
    }

    public SpellPassive(Long id, String name, String description, String pictureName, Integer mana,
                        Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins,
                        Integer updateCostDiamonds, Integer requirementHeroLevel, Integer requirementSpellPower,
                        QuantifiableWarriorCharacteristics characteristics, Integer durationSteps,
                        SpellPassive parentSpellPassive, List<SpellPassive> childrenSpellPassives) {
        super(id, name, description, pictureName, mana, purchaseCostGoldCoins, purchaseCostDiamonds, updateCostGoldCoins, updateCostDiamonds, requirementHeroLevel, requirementSpellPower);
        this.characteristics = characteristics;
        this.durationSteps = durationSteps;
        this.parentSpellPassive = parentSpellPassive;
        this.childrenSpellPassives = childrenSpellPassives;
    }

    public QuantifiableWarriorCharacteristics getCharacteristics() {
        return characteristics;
    }

    public Integer getDurationSteps() {
        return durationSteps;
    }

    public SpellPassive getParentSpellPassive() {
        return parentSpellPassive;
    }

    public List<SpellPassive> getChildrenSpellPassives() {
        return childrenSpellPassives;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpellPassive that = (SpellPassive) o;

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
