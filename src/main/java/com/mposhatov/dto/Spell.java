package com.mposhatov.dto;

public class Spell {
    protected Long id;
    protected String name;
    protected String description;
    protected String pictureName;
    protected Integer mana;
    protected Integer purchaseCostGoldCoins;
    protected Integer purchaseCostDiamonds;
    protected Integer updateCostGoldCoins;
    protected Integer updateCostDiamonds;
    protected Integer requirementHeroLevel;

    public Spell() {
    }

    public Spell(Long id, String name, String description, String pictureName, Integer mana, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
        this.mana = mana;
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

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Integer getMana() {
        return mana;
    }

    public void setMana(Integer mana) {
        this.mana = mana;
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
