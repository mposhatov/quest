package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "WARRIOR_DESCRIPTION")
public class DbWarriorDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 100, nullable = false)
    private String description;

    @Column(name = "PICTURE_NAME", length = 20, nullable = false)
    private String pictureName;

    @Column(name = "STAGE", nullable = false)
    private Integer stage;

    @Column(name = "KILLED_EXPERIENCE", nullable = false)
    private Integer killedExperience;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "WARRIOR_SHOP_CHARACTERISTICS_ID", nullable = false)
    private DbWarriorShopCharacteristics warriorShopCharacteristics;

    protected DbWarriorDescription() {
    }

    public DbWarriorDescription(String name, String description, String pictureName,
                                Integer stage, Integer killedExperience,
                                DbWarriorShopCharacteristics warriorShopCharacteristics) {
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
        this.stage = stage;
        this.killedExperience = killedExperience;
        this.warriorShopCharacteristics = warriorShopCharacteristics;
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

    public Integer getStage() {
        return stage;
    }

    public Integer getKilledExperience() {
        return killedExperience;
    }

    public DbWarriorShopCharacteristics getWarriorShopCharacteristics() {
        return warriorShopCharacteristics;
    }
}
