package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HIERARCHY_WARRIOR")
public class DbHierarchyWarrior {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 200, nullable = false)
    private String description;

    @Column(name = "LEVEL", nullable = false)
    private Integer level;

    @Column(name = "PICTURE_NAME", length = 20, nullable = false)
    private String pictureName;

    @Column(name = "KILLED_EXPERIENCE", nullable = false)
    private Long killedExperience;

    @Column(name = "IMPROVEMENT_EXPERIENCE", nullable = false)
    private Long improvementExperience;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "HIERARCHY_WARRIOR_SPELL_ATTACK",
            joinColumns = {@JoinColumn(name = "HIERARCHY_WARRIOR_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SPELL_ATTACK_ID", nullable = false)})
    private List<DbSpellAttack> spellAttacks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "HIERARCHY_WARRIOR_SPELL_HEAL",
            joinColumns = {@JoinColumn(name = "HIERARCHY_WARRIOR_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SPELL_HEAL_ID", nullable = false)})
    private List<DbSpellHeal> spellHeals = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "HIERARCHY_WARRIOR_SPELL_EXHORTATION",
            joinColumns = {@JoinColumn(name = "HIERARCHY_WARRIOR_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SPELL_EXHORTATION_ID", nullable = false)})
    private List<DbSpellExhortation> spellExhortations = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "HIERARCHY_WARRIOR_SPELL_PASSIVE",
            joinColumns = {@JoinColumn(name = "HIERARCHY_WARRIOR_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SPELL_PASSIVE_ID", nullable = false)})
    private List<DbSpellPassive> spellPassives = new ArrayList<>();

    protected DbHierarchyWarrior() {
    }

    public DbHierarchyWarrior(String name, String description, Integer level, String pictureName, Long killedExperience, Long improvementExperience, DbWarriorCharacteristics warriorCharacteristics, Integer purchaseCostGoldCoins, Integer purchaseCostDiamonds, Integer updateCostGoldCoins, Integer updateCostDiamonds, Integer requirementHeroLevel) {
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

    public DbHierarchyWarrior addChildrenHierarchyWarrior(DbHierarchyWarrior hierarchyWarrior) {
        this.childrenHierarchyWarriors.add(hierarchyWarrior);
        return this;
    }

    public DbHierarchyWarrior addChildrenHierarchyWarriors(List<DbHierarchyWarrior> hierarchyWarriors) {
        this.childrenHierarchyWarriors.addAll(hierarchyWarriors);
        return this;
    }

    public DbHierarchyWarrior addSpellAttack(DbSpellAttack spellAttack) {
        this.spellAttacks.add(spellAttack);
        return this;
    }

    public DbHierarchyWarrior addSpellAttacks(List<DbSpellAttack> spellAttacks) {
        this.spellAttacks.addAll(spellAttacks);
        return this;
    }

    public DbHierarchyWarrior addSpellHeal(DbSpellHeal spellHeal) {
        this.spellHeals.add(spellHeal);
        return this;
    }

    public DbHierarchyWarrior addSpellHeals(List<DbSpellHeal> spellHeals) {
        this.spellHeals.addAll(spellHeals);
        return this;
    }

    public DbHierarchyWarrior addSpellExhortation(DbSpellExhortation spellExhortation) {
        this.spellExhortations.add(spellExhortation);
        return this;
    }

    public DbHierarchyWarrior addSpellExhortations(List<DbSpellExhortation> spellExhortations) {
        this.spellExhortations.addAll(spellExhortations);
        return this;
    }

    public DbHierarchyWarrior addSpellPassive(DbSpellPassive spellPassive) {
        this.spellPassives.add(spellPassive);
        return this;
    }

    public DbHierarchyWarrior addSpellPassives(List<DbSpellPassive> spellPassives) {
        this.spellPassives.addAll(spellPassives);
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

    public Long getKilledExperience() {
        return killedExperience;
    }

    public Long getImprovementExperience() {
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

    public List<DbSpellAttack> getSpellAttacks() {
        return spellAttacks;
    }

    public List<DbSpellHeal> getSpellHeals() {
        return spellHeals;
    }

    public List<DbSpellExhortation> getSpellExhortations() {
        return spellExhortations;
    }

    public List<DbSpellPassive> getSpellPassives() {
        return spellPassives;
    }
}
