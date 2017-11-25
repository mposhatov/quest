package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SPELL_ATTACK")
public class DbSpellAttack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 200, nullable = false)
    private String description;

    @Column(name = "PICTURE_NAME", length = 20, nullable = false)
    private String pictureName;

    @Column(name = "DAMAGE", nullable = false)
    private Integer damage;

    @Column(name = "DAMAGE_BY_SPELL_POWER", nullable = false)
    private Integer damageBySpellPower;

    @Column(name = "MANA", nullable = false)
    private Integer mana;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_SPELL_ATTACK_ID", nullable = true)
    private DbSpellAttack parentSpellAttack;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "parentSpellAttack")
    private List<DbSpellAttack> childrenSpellAttacks = new ArrayList<>();

    @Column(name = "PURCHASE_COST_GOLD_COINS", nullable = false)
    private Integer purchaseCostGoldCoins;

    @Column(name = "PURCHASE_COST_DIAMONDS", nullable = false)
    private Integer purchaseCostDiamonds;

    @Column(name = "UPDATE_COST_GOLD_COINS", nullable = false)
    private Integer updateCostGoldCoins;

    @Column(name = "UPDATE_COST_DIAMONDS", nullable = false)
    private Integer updateCostDiamonds;

    @Column(name = "REQUIREMENT_HERO_LEVEL", nullable = false)
    private Integer requirementHeroLevel;

    protected DbSpellAttack() {
    }

    public DbSpellAttack(String name, String description, String pictureName, Integer damage, Integer damageBySpellPower, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel) {
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
        this.damage = damage;
        this.damageBySpellPower = damageBySpellPower;
        this.mana = mana;
        this.purchaseCostGoldCoins = purchaseCostGoldCoins;
        this.purchaseCostDiamonds = purchaseCostDiamonds;
        this.updateCostGoldCoins = updateCostGoldCoins;
        this.updateCostDiamonds = updateCostDiamonds;
        this.requirementHeroLevel = requirementHeroLevel;
    }

    public DbSpellAttack addChildrenSpellAttack(DbSpellAttack spellAttack) {
        this.childrenSpellAttacks.add(spellAttack);
        return this;
    }

    public DbSpellAttack addChildrenSpellAttacks(List<DbSpellAttack> spellAttacks) {
        this.childrenSpellAttacks.addAll(spellAttacks);
        return this;
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

    public DbSpellAttack getParentSpellAttack() {
        return parentSpellAttack;
    }

    public void setParentSpellAttack(DbSpellAttack parentSpellAttack) {
        this.parentSpellAttack = parentSpellAttack;
    }

    public List<DbSpellAttack> getChildrenSpellAttacks() {
        return childrenSpellAttacks;
    }

    public void setChildrenSpellAttacks(List<DbSpellAttack> childrenSpellAttacks) {
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

    public Integer getMana() {
        return mana;
    }

    public String getPictureName() {
        return pictureName;
    }
}
