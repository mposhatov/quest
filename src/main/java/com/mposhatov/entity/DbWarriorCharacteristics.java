package com.mposhatov.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "WARRIOR_CHARACTERISTICS")
public class DbWarriorCharacteristics extends AllWarriorCharacteristics {

    @Id
    @GeneratedValue(generator = "warrior")
    @GenericGenerator(name = "warrior", strategy = "foreign", parameters = {@org.hibernate.annotations.Parameter(name = "property", value = "warrior")})
    @Column(name = "WARRIOR_ID")
    private Long warriorId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WARRIOR_ID", nullable = false)
    private DbWarrior warrior;

    protected DbWarriorCharacteristics() {
    }

    public DbWarriorCharacteristics(DbWarrior warrior, DbWarriorShopCharacteristics warriorShopCharacteristics) {
        CharacteristicsMerge.mapPlusWarriorCharacteristics(this, warriorShopCharacteristics);
        this.warrior = warrior;
        this.attackType = warriorShopCharacteristics.getAttackType();
        this.rangeType = warriorShopCharacteristics.getRangeType();
    }

    public Long getWarriorId() {
        return warriorId;
    }

    public DbWarrior getWarrior() {
        return warrior;
    }

    public AttackType getAttackType() {
        return attackType;
    }
}
