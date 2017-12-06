package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SPELL_HEAL")
public class DbSpellHeal extends Spell {

    @Column(name = "HEALTH", nullable = false)
    private Integer health;

    @Column(name = "HEALTH_BY_SPELL_POWER", nullable = false)
    private Integer healthBySpellPower;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_SPELL_HEAL_ID", nullable = true)
    private DbSpellHeal parentSpellHeal;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "parentSpellHeal")
    private List<DbSpellHeal> childrenSpellHeals = new ArrayList<>();

    protected DbSpellHeal() {
    }

    public DbSpellHeal(String name, String description, String pictureName, Target target, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer health, Integer healthBySpellPower, Integer requirementSpellPower) {
        super(name, description, pictureName, target, mana, purchaseCostGoldCoins, purchaseCostDiamonds, updateCostGoldCoins, updateCostDiamonds, requirementHeroLevel, requirementSpellPower);
        this.health = health;
        this.healthBySpellPower = healthBySpellPower;
    }

    public DbSpellHeal addChildrenSpellHeals(DbSpellHeal spellHeal) {
        this.childrenSpellHeals.add(spellHeal);
        spellHeal.parentSpellHeal = this;
        return this;
    }

    public Integer getHealth() {
        return health;
    }

    public Integer getHealthBySpellPower() {
        return healthBySpellPower;
    }

    public DbSpellHeal getParentSpellHeal() {
        return parentSpellHeal;
    }

    public List<DbSpellHeal> getChildrenSpellHeals() {
        return childrenSpellHeals;
    }
}
