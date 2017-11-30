package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SPELL_ATTACK")
public class DbSpellAttack extends Spell {

    @Column(name = "DAMAGE", nullable = false)
    private Integer damage;

    @Column(name = "DAMAGE_BY_SPELL_POWER", nullable = false)
    private Integer damageBySpellPower;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_SPELL_ATTACK_ID", nullable = true)
    private DbSpellAttack parentSpellAttack;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "parentSpellAttack")
    private List<DbSpellAttack> childrenSpellAttacks = new ArrayList<>();

    protected DbSpellAttack() {
    }

    public DbSpellAttack(String name, String description, String pictureName, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer damage, Integer damageBySpellPower, Integer requirementSpellPower) {
        super(name, description, pictureName, mana, purchaseCostGoldCoins, purchaseCostDiamonds, updateCostGoldCoins, updateCostDiamonds, requirementHeroLevel, requirementSpellPower);
        this.damage = damage;
        this.damageBySpellPower = damageBySpellPower;
    }

    public DbSpellAttack addChildrenSpellAttack(DbSpellAttack spellAttack) {
        this.childrenSpellAttacks.add(spellAttack);
        return this;
    }

    public DbSpellAttack addChildrenSpellAttacks(List<DbSpellAttack> spellAttacks) {
        this.childrenSpellAttacks.addAll(spellAttacks);
        return this;
    }

    public Integer getDamage() {
        return damage;
    }

    public Integer getDamageBySpellPower() {
        return damageBySpellPower;
    }

    public DbSpellAttack getParentSpellAttack() {
        return parentSpellAttack;
    }

    public List<DbSpellAttack> getChildrenSpellAttacks() {
        return childrenSpellAttacks;
    }
}
