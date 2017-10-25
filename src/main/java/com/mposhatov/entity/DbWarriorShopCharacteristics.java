package com.mposhatov.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "WARRIOR_SHOP_CHARACTERISTICS")
public class DbWarriorShopCharacteristics extends MainWarriorCharacteristics {

    @Id
    @GeneratedValue(generator = "warrior_shop")
    @GenericGenerator(name = "warrior_shop", strategy = "foreign", parameters = {@org.hibernate.annotations.Parameter(name = "property", value = "warrior_shop")})
    @Column(name = "WARRIOR_SHOP_ID")
    private Long warriorShopId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WARRIOR_SHOP_ID", nullable = false)
    private DbWarriorShop warriorShop;

    @Convert(converter = AttackTypeConverter.class)
    @Column(name = "ATTACK_TYPE", nullable = false)
    protected AttackType attackType;

    protected DbWarriorShopCharacteristics() {
    }

    public DbWarriorShopCharacteristics(DbWarriorShop warriorShop, long attack, AttackType attackType,
                                        long physicalDefense, long magicDefense,
                                        long health, long minDamage, long maxDamage, long velocity) {

        super(attack, physicalDefense, magicDefense, health, minDamage, maxDamage, velocity);
        this.warriorShop = warriorShop;
        this.attackType = attackType;
    }

    public Long getWarriorShopId() {
        return warriorShopId;
    }

    public DbWarriorShop getWarriorShop() {
        return warriorShop;
    }

    public AttackType getAttackType() {
        return attackType;
    }
}
