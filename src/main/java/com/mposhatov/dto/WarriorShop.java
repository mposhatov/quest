package com.mposhatov.dto;

public class WarriorShop {
    private Long id;
    private String name;
    private String description;
    private String pictureName;
    private Integer stage;
    private long priceOfGoldenCoins;
    private long priceOfDiamonds;
    private WarriorCharacteristics warriorCharacteristics;

    public WarriorShop() {
    }

    public WarriorShop(Long id, String name, String description, String pictureName, Integer stage, long priceOfGoldenCoins, long priceOfDiamonds, WarriorCharacteristics warriorCharacteristics) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
        this.stage = stage;
        this.priceOfGoldenCoins = priceOfGoldenCoins;
        this.priceOfDiamonds = priceOfDiamonds;
        this.warriorCharacteristics = warriorCharacteristics;
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

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public long getPriceOfGoldenCoins() {
        return priceOfGoldenCoins;
    }

    public void setPriceOfGoldenCoins(long priceOfGoldenCoins) {
        this.priceOfGoldenCoins = priceOfGoldenCoins;
    }

    public long getPriceOfDiamonds() {
        return priceOfDiamonds;
    }

    public void setPriceOfDiamonds(long priceOfDiamonds) {
        this.priceOfDiamonds = priceOfDiamonds;
    }

    public WarriorCharacteristics getWarriorCharacteristics() {
        return warriorCharacteristics;
    }

    public void setWarriorCharacteristics(WarriorCharacteristics warriorCharacteristics) {
        this.warriorCharacteristics = warriorCharacteristics;
    }
}
