package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SPELL_PASSIVE")
public class DbSpellPassive extends Spell {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "spellPassive")
    private DbSpellPassiveCharacteristics spellPassiveCharacteristics;

    @Column(name = "DURATION_STEPS", nullable = false)
    private Integer durationSteps;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_SPELL_PASSIVE_ID", nullable = true)
    private DbSpellPassive parentSpellPassive;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "parentSpellPassive")
    private List<DbSpellPassive> childrenSpellPassives = new ArrayList<>();

    protected DbSpellPassive() {
    }

    public DbSpellPassive(String name, String description, String pictureName, Target target, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer requirementSpellPower, DbSpellPassiveCharacteristics spellPassiveCharacteristics, Integer durationSteps) {
        super(name, description, pictureName, target, mana, purchaseCostGoldCoins, purchaseCostDiamonds, updateCostGoldCoins, updateCostDiamonds, requirementHeroLevel, requirementSpellPower);
        this.spellPassiveCharacteristics = spellPassiveCharacteristics;
        this.durationSteps = durationSteps;
    }

    public DbSpellPassiveCharacteristics getSpellPassiveCharacteristics() {
        return spellPassiveCharacteristics;
    }

    public Integer getDurationSteps() {
        return durationSteps;
    }

    public DbSpellPassive getParentSpellPassive() {
        return parentSpellPassive;
    }

    public List<DbSpellPassive> getChildrenSpellPassives() {
        return childrenSpellPassives;
    }
}
