package com.mposhatov.dto;

public class Spell {
    protected Long id;
    protected String name;
    protected String description;
    protected String pictureName;
    protected Target target;
    protected Integer mana;
    protected Integer purchaseCostGoldCoins;
    protected Integer purchaseCostDiamonds;
    protected Integer updateCostGoldCoins;
    protected Integer updateCostDiamonds;
    protected Integer requirementHeroLevel;
    protected Integer requirementSpellPower;

    public Spell() {
    }

    public Spell(Long id, String name, String description, String pictureName, Target target, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel, Integer requirementSpellPower) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
        this.target = target;
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

    public Target getTarget() {
        return target;
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
