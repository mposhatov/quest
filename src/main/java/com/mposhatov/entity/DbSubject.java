package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SUBJECT")
public class DbSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 100, nullable = false)
    private String description;

    @Column(name = "PICTURE_NAME", nullable = false)
    private String pictureName;

    @Column(name = "PRICE_OF_GOLDEN_COINS", nullable = false)
    private long priceOfGoldenCoins;

    @Column(name = "PRICE_OF_DIAMONDS", nullable = false)
    private long priceOfDiamonds;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CHARACTERISTICS_ID", nullable = false)
    private DbCharacteristics givingCharacteristics;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SUBJECTS_OF_CLIENTS",
            joinColumns = {@JoinColumn(name = "SUBJECT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "CLIENT_ID", nullable = false)})
    private List<DbClient> heldByClients = new ArrayList<>();

    //======================================

    protected DbSubject() {
    }

    public DbSubject(String name, String description, String pictureName,
                     long priceOfGoldenCoins, long priceOfDiamonds,
                     DbCharacteristics givingCharacteristics) {
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
        this.priceOfGoldenCoins = priceOfGoldenCoins;
        this.priceOfDiamonds = priceOfDiamonds;
        this.givingCharacteristics = givingCharacteristics;
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

    public String getPictureName() {
        return pictureName;
    }

    public long getPriceOfGoldenCoins() {
        return priceOfGoldenCoins;
    }

    public long getPriceOfDiamonds() {
        return priceOfDiamonds;
    }

    public DbCharacteristics getGivingCharacteristics() {
        return givingCharacteristics;
    }

    public List<DbClient> getHeldByClients() {
        return heldByClients;
    }
}
