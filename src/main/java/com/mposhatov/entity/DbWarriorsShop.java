package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "WARRIORS_SHOP")
public class DbWarriorsShop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PRICE_OF_GOLDEN_COINS", nullable = false)
    private long priceOfGoldenCoins;

    @Column(name = "PRICE_OF_DIAMONDS", nullable = false)
    private long priceOfGoldenDiamonds;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "WARRIOR_DESCRIPTION_ID", nullable = false)
    private DbWarriorDescription creaturesDescription;

    protected DbWarriorsShop() {
    }

    public DbWarriorsShop(long priceOfGoldenCoins, long priceOfGoldenDiamonds, DbWarriorDescription creaturesDescription) {
        this.priceOfGoldenCoins = priceOfGoldenCoins;
        this.priceOfGoldenDiamonds = priceOfGoldenDiamonds;
        this.creaturesDescription = creaturesDescription;
    }

    public Long getId() {
        return id;
    }

    public long getPriceOfGoldenCoins() {
        return priceOfGoldenCoins;
    }

    public long getPriceOfGoldenDiamonds() {
        return priceOfGoldenDiamonds;
    }

    public DbWarriorDescription getCreaturesDescription() {
        return creaturesDescription;
    }
}
