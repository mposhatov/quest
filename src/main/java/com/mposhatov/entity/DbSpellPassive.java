package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SPELL_PASSIVE")
public class DbSpellPassive extends Spell {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "spellPassive")
    private DbSpellPassiveCharacteristics spellPassiveCharacteristics;

    @Column(name = "SUMMED", nullable = false)
    private Boolean summed;

    @Column(name = "MAX_SUMMED", nullable = true)
    private Integer maxSummed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_SPELL_PASSIVE_ID", nullable = true)
    private DbSpellPassive parentSpellPassive;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "parentSpellPassive")
    private List<DbSpellPassive> childrenSpellPassives = new ArrayList<>();

    protected DbSpellPassive() {

    }

    public DbSpellPassive(String name, String description, String pictureName, Target target, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer requirementSpellPower, DbSpellPassiveCharacteristics spellPassiveCharacteristics, Integer maxSummed) {
        super(name, description, pictureName, target, mana, purchaseCostGoldCoins, purchaseCostDiamonds, updateCostGoldCoins, updateCostDiamonds, requirementHeroLevel, requirementSpellPower);
        this.spellPassiveCharacteristics = spellPassiveCharacteristics;
        this.summed = true;
        this.maxSummed = maxSummed;
    }

    public DbSpellPassive(String name, String description, String pictureName, Target target, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer requirementSpellPower, DbSpellPassiveCharacteristics spellPassiveCharacteristics) {
        super(name, description, pictureName, target, mana, purchaseCostGoldCoins, purchaseCostDiamonds, updateCostGoldCoins, updateCostDiamonds, requirementHeroLevel, requirementSpellPower);
        this.spellPassiveCharacteristics = spellPassiveCharacteristics;
        this.summed = false;
    }

    public DbSpellPassive addChildrenSpellPassives(DbSpellPassive spellPassive) {
        this.childrenSpellPassives.add(spellPassive);
        spellPassive.parentSpellPassive = this;
        return this;
    }

    public DbSpellPassiveCharacteristics getSpellPassiveCharacteristics() {
        return spellPassiveCharacteristics;
    }

    public Boolean isSummed() {
        return summed;
    }


    public Integer getMaxSummed() {
        return maxSummed;
    }

    public DbSpellPassive getParentSpellPassive() {
        return parentSpellPassive;
    }

    public List<DbSpellPassive> getChildrenSpellPassives() {
        return childrenSpellPassives;
    }
}
