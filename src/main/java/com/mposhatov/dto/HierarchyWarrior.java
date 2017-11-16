package com.mposhatov.dto;

public class HierarchyWarrior {
    private Long id;
    private String name;
    private String description;
    private Integer level;
    private String pictureName;
    private Integer killedExperience;
    private Integer improvementExperience;
    private WarriorCharacteristics warriorCharacteristics;
    private HierarchyWarrior parentHierarchyWarrior;
    private HierarchyWarrior childrenHierarchyWarrior;
    private long priceOfGoldenCoins;
    private long priceOfDiamonds;

    public HierarchyWarrior(Long id, String name, String description, Integer level, String pictureName, Integer killedExperience, Integer improvementExperience, WarriorCharacteristics warriorCharacteristics, long priceOfGoldenCoins, long priceOfDiamonds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.pictureName = pictureName;
        this.killedExperience = killedExperience;
        this.improvementExperience = improvementExperience;
        this.warriorCharacteristics = warriorCharacteristics;
        this.priceOfGoldenCoins = priceOfGoldenCoins;
        this.priceOfDiamonds = priceOfDiamonds;
    }

    public HierarchyWarrior(Long id, String name, String description, Integer level, String pictureName, Integer killedExperience, Integer improvementExperience, WarriorCharacteristics warriorCharacteristics, HierarchyWarrior childrenHierarchyWarrior, long priceOfGoldenCoins, long priceOfDiamonds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.pictureName = pictureName;
        this.killedExperience = killedExperience;
        this.improvementExperience = improvementExperience;
        this.warriorCharacteristics = warriorCharacteristics;
        this.childrenHierarchyWarrior = childrenHierarchyWarrior;
        this.priceOfGoldenCoins = priceOfGoldenCoins;
        this.priceOfDiamonds = priceOfDiamonds;
    }

    public HierarchyWarrior(Long id, String name, String description, Integer level, String pictureName, Integer killedExperience, Integer improvementExperience, WarriorCharacteristics warriorCharacteristics, HierarchyWarrior parentHierarchyWarrior, HierarchyWarrior childrenHierarchyWarrior, long priceOfGoldenCoins, long priceOfDiamonds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.pictureName = pictureName;
        this.killedExperience = killedExperience;
        this.improvementExperience = improvementExperience;
        this.warriorCharacteristics = warriorCharacteristics;
        this.parentHierarchyWarrior = parentHierarchyWarrior;
        this.childrenHierarchyWarrior = childrenHierarchyWarrior;
        this.priceOfGoldenCoins = priceOfGoldenCoins;
        this.priceOfDiamonds = priceOfDiamonds;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Integer getKilledExperience() {
        return killedExperience;
    }

    public void setKilledExperience(Integer killedExperience) {
        this.killedExperience = killedExperience;
    }

    public Integer getImprovementExperience() {
        return improvementExperience;
    }

    public void setImprovementExperience(Integer improvementExperience) {
        this.improvementExperience = improvementExperience;
    }

    public WarriorCharacteristics getWarriorCharacteristics() {
        return warriorCharacteristics;
    }

    public void setWarriorCharacteristics(WarriorCharacteristics warriorCharacteristics) {
        this.warriorCharacteristics = warriorCharacteristics;
    }

    public HierarchyWarrior getParentHierarchyWarrior() {
        return parentHierarchyWarrior;
    }

    public void setParentHierarchyWarrior(HierarchyWarrior parentHierarchyWarrior) {
        this.parentHierarchyWarrior = parentHierarchyWarrior;
    }

    public HierarchyWarrior getChildrenHierarchyWarrior() {
        return childrenHierarchyWarrior;
    }

    public void setChildrenHierarchyWarrior(HierarchyWarrior childrenHierarchyWarrior) {
        this.childrenHierarchyWarrior = childrenHierarchyWarrior;
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
}
