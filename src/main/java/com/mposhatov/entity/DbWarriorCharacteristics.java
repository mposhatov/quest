package com.mposhatov.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "WARRIOR_CHARACTERISTICS")
public class DbWarriorCharacteristics extends AllWarriorCharacteristics {

    @Id
    @GeneratedValue(generator = "hierarchy_warrior")
    @GenericGenerator(name = "hierarchy_warrior", strategy = "foreign", parameters = {@org.hibernate.annotations.Parameter(name = "property", value = "hierarchy_warrior")})
    @Column(name = "HIERARCHY_WARRIOR_ID")
    private Long warriorId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HIERARCHY_WARRIOR_ID", nullable = false)
    private DbHierarchyWarrior hierarchyWarrior;

    protected DbWarriorCharacteristics() {
    }

    public DbWarriorCharacteristics(DbHierarchyWarrior hierarchyWarrior, long attack, AttackType attackType, RangeType rangeType, long physicalDefense, long magicDefense, long health, int velocity, int activatedDefensePercent) {
        super(attack, attackType, rangeType, physicalDefense, magicDefense, health, velocity, activatedDefensePercent);
        this.hierarchyWarrior = hierarchyWarrior;
    }

    public Long getWarriorId() {
        return warriorId;
    }

    public DbHierarchyWarrior getHierarchyWarrior() {
        return hierarchyWarrior;
    }
}
