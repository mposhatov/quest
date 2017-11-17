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

    @Column(name = "PRICE_OF_GOLDEN_COINS", nullable = false)
    private long priceOfGoldenCoins;

    @Column(name = "PRICE_OF_DIAMONDS", nullable = false)
    private long priceOfDiamonds;

    protected DbHierarchyWarrior() {
    }

    public DbHierarchyWarrior(String name, String description, Integer level, String pictureName, Integer killedExperience, Integer improvementExperience, DbWarriorCharacteristics warriorCharacteristics, long priceOfGoldenCoins, long priceOfDiamonds) {
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

    public long getPriceOfGoldenCoins() {
        return priceOfGoldenCoins;
    }

    public long getPriceOfDiamonds() {
        return priceOfDiamonds;
    }
}
