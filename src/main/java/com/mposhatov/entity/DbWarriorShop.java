package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "WARRIOR_SHOP")
public class DbWarriorShop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PRICE_OF_GOLDEN_COINS", nullable = false)
    private long priceOfGoldenCoins;

    @Column(name = "PRICE_OF_DIAMONDS", nullable = false)
    private long priceOfDiamonds;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "WARRIOR_DESCRIPTION_ID", nullable = false)
    private DbWarriorDescription warriorDescription;

    protected DbWarriorShop() {
    }

    public DbWarriorShop(long priceOfGoldenCoins, long priceOfDiamonds, DbWarriorDescription warriorDescription) {
        this.priceOfGoldenCoins = priceOfGoldenCoins;
        this.priceOfDiamonds = priceOfDiamonds;
        this.warriorDescription = warriorDescription;
    }

    public Long getId() {
        return id;
    }

    public long getPriceOfGoldenCoins() {
        return priceOfGoldenCoins;
    }

    public long getPriceOfDiamonds() {
        return priceOfDiamonds;
    }

    public DbWarriorDescription getWarriorDescription() {
        return warriorDescription;
    }
}
