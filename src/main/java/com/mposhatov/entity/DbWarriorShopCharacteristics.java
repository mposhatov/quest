package com.mposhatov.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "WARRIOR_SHOP_CHARACTERISTICS")
public class DbWarriorShopCharacteristics extends AllWarriorCharacteristics {

    @Id
    @GeneratedValue(generator = "warrior_shop")
    @GenericGenerator(name = "warrior_shop", strategy = "foreign", parameters = {@org.hibernate.annotations.Parameter(name = "property", value = "warrior_shop")})
    @Column(name = "WARRIOR_SHOP_ID")
    private Long warriorShopId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WARRIOR_SHOP_ID", nullable = false)
    private DbWarriorShop warriorShop;

    protected DbWarriorShopCharacteristics() {
    }

    public DbWarriorShopCharacteristics(DbWarriorShop warriorShop, long attack, AttackType attackType, RangeType rangeType,
                                        long physicalDefense, long magicDefense, long health, int velocity, int activatedDefensePercent) {
        super(attack, attackType, rangeType, physicalDefense, magicDefense, health, velocity, activatedDefensePercent);
        this.warriorShop = warriorShop;
    }

    public Long getWarriorShopId() {
        return warriorShopId;
    }

    public DbWarriorShop getWarriorShop() {
        return warriorShop;
    }
}
