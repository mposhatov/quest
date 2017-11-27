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

    public DbSpellHeal(String name, String description, String pictureName, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer health, Integer healthBySpellPower) {
        super(name, description, pictureName, mana, purchaseCostGoldCoins, purchaseCostDiamonds, updateCostGoldCoins, updateCostDiamonds, requirementHeroLevel);
        this.health = health;
        this.healthBySpellPower = healthBySpellPower;
    }

    public DbSpellHeal addChildrenSpellHeals(DbSpellHeal dbSpellHeal) {
        this.childrenSpellHeals.add(dbSpellHeal);
        return this;
    }

    public DbSpellHeal addChildrenSpellHeals(List<DbSpellHeal> spellHeals) {
        this.childrenSpellHeals.addAll(spellHeals);
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
