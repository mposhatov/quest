package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "SUBJECTS_SHOP")
public class DbSubjectsShop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PRICE_OF_GOLD_COINS", nullable = false)
    private long priceOfGoldCoins;

    @Column(name = "PRICE_OF_DIAMONDS", nullable = false)
    private long priceOfGoldenDiamonds;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBJECT_DESCRIPTION_ID", nullable = false)
    private DbSubjectDescription subjectDescription;

    protected DbSubjectsShop() {
    }

    public DbSubjectsShop(long priceOfGoldCoins, long priceOfGoldenDiamonds, DbSubjectDescription subjectDescription) {
        this.priceOfGoldCoins = priceOfGoldCoins;
        this.priceOfGoldenDiamonds = priceOfGoldenDiamonds;
        this.subjectDescription = subjectDescription;
    }

    public Long getId() {
        return id;
    }

    public long getPriceOfGoldCoins() {
        return priceOfGoldCoins;
    }

    public long getPriceOfGoldenDiamonds() {
        return priceOfGoldenDiamonds;
    }

    public DbSubjectDescription getSubjectDescription() {
        return subjectDescription;
    }
}
