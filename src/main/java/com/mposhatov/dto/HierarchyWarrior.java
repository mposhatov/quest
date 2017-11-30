package com.mposhatov.dto;

import java.util.ArrayList;
import java.util.List;

public class HierarchyWarrior {
    private Long id;
    private String name;
    private String description;
    private Integer level;
    private String pictureName;
    private Long killedExperience;
    private Long improvementExperience;
    private WarriorCharacteristics warriorCharacteristics;
    private HierarchyWarrior parentHierarchyWarrior;
    private List<HierarchyWarrior> childrenHierarchyWarriors;
    private Integer priceOfGoldenCoins;
    private Integer priceOfDiamonds;
    private List<SpellAttack> spellAttacks = new ArrayList<>();
    private List<SpellHeal> spellHeals = new ArrayList<>();
    private List<SpellExhortation> spellExhortations = new ArrayList<>();
    private List<SpellPassive> spellPassives = new ArrayList<>();

    public HierarchyWarrior(Long id, String name, String description, Integer level, String pictureName,
                            Long killedExperience, Long improvementExperience, WarriorCharacteristics warriorCharacteristics,
                            HierarchyWarrior parentHierarchyWarrior, List<HierarchyWarrior> childrenHierarchyWarriors, Integer priceOfGoldenCoins, Integer priceOfDiamonds,
                            List<SpellAttack> spellAttacks, List<SpellHeal> spellHeals, List<SpellExhortation> spellExhortations, List<SpellPassive> spellPassives) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.pictureName = pictureName;
        this.killedExperience = killedExperience;
        this.improvementExperience = improvementExperience;
        this.warriorCharacteristics = warriorCharacteristics;
        this.parentHierarchyWarrior = parentHierarchyWarrior;
        this.childrenHierarchyWarriors = childrenHierarchyWarriors;
        this.priceOfGoldenCoins = priceOfGoldenCoins;
        this.priceOfDiamonds = priceOfDiamonds;
        this.spellAttacks = spellAttacks;
        this.spellHeals = spellHeals;
        this.spellExhortations = spellExhortations;
        this.spellPassives = spellPassives;
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

    public Long getKilledExperience() {
        return killedExperience;
    }

    public void setKilledExperience(Long killedExperience) {
        this.killedExperience = killedExperience;
    }

    public Long getImprovementExperience() {
        return improvementExperience;
    }

    public void setImprovementExperience(Long improvementExperience) {
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

    public List<HierarchyWarrior> getChildrenHierarchyWarriors() {
        return childrenHierarchyWarriors;
    }

    public void setChildrenHierarchyWarriors(List<HierarchyWarrior> childrenHierarchyWarriors) {
        this.childrenHierarchyWarriors = childrenHierarchyWarriors;
    }

    public long getPriceOfGoldenCoins() {
        return priceOfGoldenCoins;
    }

    public void setPriceOfGoldenCoins(Integer priceOfGoldenCoins) {
        this.priceOfGoldenCoins = priceOfGoldenCoins;
    }

    public long getPriceOfDiamonds() {
        return priceOfDiamonds;
    }

    public void setPriceOfDiamonds(Integer priceOfDiamonds) {
        this.priceOfDiamonds = priceOfDiamonds;
    }

    public List<SpellAttack> getSpellAttacks() {
        return spellAttacks;
    }

    public List<SpellHeal> getSpellHeals() {
        return spellHeals;
    }

    public List<SpellExhortation> getSpellExhortations() {
        return spellExhortations;
    }

    public List<SpellPassive> getSpellPassives() {
        return spellPassives;
    }
}
