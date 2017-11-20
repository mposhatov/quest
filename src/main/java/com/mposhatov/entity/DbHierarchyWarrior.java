package com.mposhatov.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "HIERARCHY_WARRIOR")
public class DbHierarchyWarrior {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 100, nullable = false)
    private String description;

    @Column(name = "LEVEL", nullable = false)
    private Integer level;

    @Column(name = "PICTURE_NAME", length = 20, nullable = false)
    private String pictureName;

    @Column(name = "KILLED_EXPERIENCE", nullable = false)
    private Integer killedExperience;

    @Column(name = "IMPROVEMENT_EXPERIENCE", nullable = false)
    private Integer improvementExperience;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "WARRIOR_CHARACTERISTICS_ID", nullable = false)
    private DbWarriorCharacteristics warriorCharacteristics;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_HIERARCHY_WARRIOR_ID", nullable = true)
    private DbHierarchyWarrior parentHierarchyWarrior;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "parentHierarchyWarrior")
    private List<DbHierarchyWarrior> childrenHierarchyWarriors;

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

    protected DbHierarchyWarrior() {
    }

    public DbHierarchyWarrior(String name, String description, Integer level, String pictureName, Integer killedExperience, Integer improvementExperience, DbWarriorCharacteristics warriorCharacteristics, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.pictureName = pictureName;
        this.killedExperience = killedExperience;
        this.improvementExperience = improvementExperience;
        this.warriorCharacteristics = warriorCharacteristics;
        this.purchaseCostGoldCoins = purchaseCostGoldCoins;
        this.purchaseCostDiamonds = purchaseCostDiamonds;
        this.updateCostGoldCoins = updateCostGoldCoins;
        this.updateCostDiamonds = updateCostDiamonds;
        this.requirementHeroLevel = requirementHeroLevel;
    }

    public DbHierarchyWarrior(String name, String description, Integer level, String pictureName, Integer killedExperience, Integer improvementExperience, DbWarriorCharacteristics warriorCharacteristics, DbHierarchyWarrior parentHierarchyWarrior, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.pictureName = pictureName;
        this.killedExperience = killedExperience;
        this.improvementExperience = improvementExperience;
        this.warriorCharacteristics = warriorCharacteristics;
        this.parentHierarchyWarrior = parentHierarchyWarrior;
        this.purchaseCostGoldCoins = purchaseCostGoldCoins;
        this.purchaseCostDiamonds = purchaseCostDiamonds;
        this.updateCostGoldCoins = updateCostGoldCoins;
        this.updateCostDiamonds = updateCostDiamonds;
        this.requirementHeroLevel = requirementHeroLevel;
    }

    public DbHierarchyWarrior(String name, String description, Integer level, String pictureName, Integer killedExperience, Integer improvementExperience, DbWarriorCharacteristics warriorCharacteristics, List<DbHierarchyWarrior> childrenHierarchyWarriors, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.pictureName = pictureName;
        this.killedExperience = killedExperience;
        this.improvementExperience = improvementExperience;
        this.warriorCharacteristics = warriorCharacteristics;
        this.childrenHierarchyWarriors = childrenHierarchyWarriors;
        this.purchaseCostGoldCoins = purchaseCostGoldCoins;
        this.purchaseCostDiamonds = purchaseCostDiamonds;
        this.updateCostGoldCoins = updateCostGoldCoins;
        this.updateCostDiamonds = updateCostDiamonds;
        this.requirementHeroLevel = requirementHeroLevel;
    }

    public DbHierarchyWarrior(String name, String description, Integer level, String pictureName, Integer killedExperience, Integer improvementExperience, DbWarriorCharacteristics warriorCharacteristics, DbHierarchyWarrior parentHierarchyWarrior, List<DbHierarchyWarrior> childrenHierarchyWarriors, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.pictureName = pictureName;
        this.killedExperience = killedExperience;
        this.improvementExperience = improvementExperience;
        this.warriorCharacteristics = warriorCharacteristics;
        this.parentHierarchyWarrior = parentHierarchyWarrior;
        this.childrenHierarchyWarriors = childrenHierarchyWarriors;
        this.purchaseCostGoldCoins = purchaseCostGoldCoins;
        this.purchaseCostDiamonds = purchaseCostDiamonds;
        this.updateCostGoldCoins = updateCostGoldCoins;
        this.updateCostDiamonds = updateCostDiamonds;
        this.requirementHeroLevel = requirementHeroLevel;
    }

    public DbHierarchyWarrior addChildrenHierarchyWarrior(DbHierarchyWarrior hierarchyWarrior) {
        this.childrenHierarchyWarriors.add(hierarchyWarrior);
        return this;
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

    public Integer getLevel() {
        return level;
    }

    public String getPictureName() {
        return pictureName;
    }

    public Integer getKilledExperience() {
        return killedExperience;
    }

    public Integer getImprovementExperience() {
        return improvementExperience;
    }

    public DbWarriorCharacteristics getWarriorCharacteristics() {
        return warriorCharacteristics;
    }

    public DbHierarchyWarrior getParentHierarchyWarrior() {
        return parentHierarchyWarrior;
    }

    public List<DbHierarchyWarrior> getChildrenHierarchyWarriors() {
        return childrenHierarchyWarriors;
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
}
