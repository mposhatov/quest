package com.mposhatov.dto;

import java.util.ArrayList;
import java.util.List;

public class SpellAttack {
    private Long id;
    private String name;
    private String description;
    private Integer damage;
    private Integer damageBySpellPower;
    private SpellAttack parentSpellAttack;
    private List<SpellAttack> childrenSpellAttacks = new ArrayList<>();
    private Integer purchaseCostGoldCoins;
    private Integer purchaseCostDiamonds;
    private Integer updateCostGoldCoins;
    private Integer updateCostDiamonds;
    private Integer requirementHeroLevel;

    public SpellAttack() {
    }

    public SpellAttack(Long id, String name, String description, Integer damage, Integer damageBySpellPower, SpellAttack parentSpellAttack, List<SpellAttack> childrenSpellAttacks, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.damageBySpellPower = damageBySpellPower;
        this.parentSpellAttack = parentSpellAttack;
        this.childrenSpellAttacks = childrenSpellAttacks;
        this.purchaseCostGoldCoins = purchaseCostGoldCoins;
        this.purchaseCostDiamonds = purchaseCostDiamonds;
        this.updateCostGoldCoins = updateCostGoldCoins;
        this.updateCostDiamonds = updateCostDiamonds;
        this.requirementHeroLevel = requirementHeroLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getDamageBySpellPower() {
        return damageBySpellPower;
    }

    public void setDamageBySpellPower(Integer damageBySpellPower) {
        this.damageBySpellPower = damageBySpellPower;
    }

    public SpellAttack getParentSpellAttack() {
        return parentSpellAttack;
    }

    public void setParentSpellAttack(SpellAttack parentSpellAttack) {
        this.parentSpellAttack = parentSpellAttack;
    }

    public List<SpellAttack> getChildrenSpellAttacks() {
        return childrenSpellAttacks;
    }

    public void setChildrenSpellAttacks(List<SpellAttack> childrenSpellAttacks) {
        this.childrenSpellAttacks = childrenSpellAttacks;
    }

    public Integer getPurchaseCostGoldCoins() {
        return purchaseCostGoldCoins;
    }

    public void setPurchaseCostGoldCoins(Integer purchaseCostGoldCoins) {
        this.purchaseCostGoldCoins = purchaseCostGoldCoins;
    }

    public Integer getPurchaseCostDiamonds() {
        return purchaseCostDiamonds;
    }

    public void setPurchaseCostDiamonds(Integer purchaseCostDiamonds) {
        this.purchaseCostDiamonds = purchaseCostDiamonds;
    }

    public Integer getUpdateCostGoldCoins() {
        return updateCostGoldCoins;
    }

    public void setUpdateCostGoldCoins(Integer updateCostGoldCoins) {
        this.updateCostGoldCoins = updateCostGoldCoins;
    }

    public Integer getUpdateCostDiamonds() {
        return updateCostDiamonds;
    }

    public void setUpdateCostDiamonds(Integer updateCostDiamonds) {
        this.updateCostDiamonds = updateCostDiamonds;
    }

    public Integer getRequirementHeroLevel() {
        return requirementHeroLevel;
    }

    public void setRequirementHeroLevel(Integer requirementHeroLevel) {
        this.requirementHeroLevel = requirementHeroLevel;
    }
}