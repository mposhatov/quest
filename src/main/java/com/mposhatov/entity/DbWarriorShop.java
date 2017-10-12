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
    private DbWarriorDescription creaturesDescription;

    protected DbWarriorShop() {
    }

    public DbWarriorShop(long priceOfGoldenCoins, long priceOfDiamonds, DbWarriorDescription creaturesDescription) {
        this.priceOfGoldenCoins = priceOfGoldenCoins;
        this.priceOfDiamonds = priceOfDiamonds;
        this.creaturesDescription = creaturesDescription;
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

    public DbWarriorDescription getCreaturesDescription() {
        return creaturesDescription;
    }
}
