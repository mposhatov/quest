package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SPELL_EXHORTATION")
public class DbSpellExhortation extends Spell {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HIERARCHY_WARRIOR_ID", nullable = false)
    private DbHierarchyWarrior hierarchyWarrior;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_SPELL_EXHORTATION_ID", nullable = true)
    private DbSpellExhortation parentSpellExhortation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "parentSpellExhortation")
    private List<DbSpellExhortation> childrenSpellExhortations = new ArrayList<>();

    protected DbSpellExhortation() {
    }

    public DbSpellExhortation(String name, String description, String pictureName, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer requirementSpellPower, DbHierarchyWarrior hierarchyWarrior, DbSpellExhortation parentSpellExhortation, List<DbSpellExhortation> childrenSpellExhortations) {
        super(name, description, pictureName, mana, purchaseCostGoldCoins, purchaseCostDiamonds, updateCostGoldCoins, updateCostDiamonds, requirementHeroLevel, requirementSpellPower);
        this.hierarchyWarrior = hierarchyWarrior;
        this.parentSpellExhortation = parentSpellExhortation;
        this.childrenSpellExhortations = childrenSpellExhortations;
    }

    public DbHierarchyWarrior getHierarchyWarrior() {
        return hierarchyWarrior;
    }

    public DbSpellExhortation getParentSpellExhortation() {
        return parentSpellExhortation;
    }

    public List<DbSpellExhortation> getChildrenSpellExhortations() {
        return childrenSpellExhortations;
    }
}
