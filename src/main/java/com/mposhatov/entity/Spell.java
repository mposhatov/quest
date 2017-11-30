package com.mposhatov.entity;

import javax.persistence.*;

@MappedSuperclass
public class Spell {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 200, nullable = false)
    private String description;

    @Column(name = "PICTURE_NAME", length = 20, nullable = false)
    private String pictureName;

    @Column(name = "MANA", nullable = false)
    private Integer mana;

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

    @Column(name = "REQUIREMENT_SPELL_POWER", nullable = false)
    private Integer requirementSpellPower;

    protected Spell() {
    }

    public Spell(String name, String description, String pictureName, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer requirementSpellPower) {
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
        this.mana = mana;
        this.purchaseCostGoldCoins = purchaseCostGoldCoins;
        this.purchaseCostDiamonds = purchaseCostDiamonds;
        this.updateCostGoldCoins = updateCostGoldCoins;
        this.updateCostDiamonds = updateCostDiamonds;
        this.requirementHeroLevel = requirementHeroLevel;
        this.requirementSpellPower = requirementSpellPower;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureName() {
        return pictureName;
    }

    public Integer getMana() {
        return mana;
    }

    public Integer getPurchaseCostGoldCoins() {
        return purchaseCostGoldCoins;
    }

    public Integer getPurchaseCostDiamonds() {
        return purchaseCostDiamonds;
    }

    public Integer getUpdateCostGoldCoins() {
        return updateCostGoldCoins;
    }

    public Integer getUpdateCostDiamonds() {
        return updateCostDiamonds;
    }

    public Integer getRequirementHeroLevel() {
        return requirementHeroLevel;
    }

    public Integer getRequirementSpellPower() {
        return requirementSpellPower;
    }
}
