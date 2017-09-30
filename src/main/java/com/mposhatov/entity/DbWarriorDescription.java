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
    private int stage;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "START_WARRIOR_CHARACTERISTICS_ID", nullable = false)
    private DbWarriorCharacteristics startWarriorCharacteristics;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "WARRIOR_CHARACTERISTICS_BY_LEVEL_ID", nullable = false)
    private DbWarriorCharacteristics warriorCharacteristicsByLevel;

    protected DbWarriorDescription() {
    }

    public DbWarriorDescription(String name, String description, String pictureName, int stage,
                                DbWarriorCharacteristics startWarriorCharacteristics,
                                DbWarriorCharacteristics warriorCharacteristicsByLevel) {
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
        this.stage = stage;
        this.startWarriorCharacteristics = startWarriorCharacteristics;
        this.warriorCharacteristicsByLevel = warriorCharacteristicsByLevel;
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

    public int getStage() {
        return stage;
    }

    public String getPictureName() {
        return pictureName;
    }

    public DbWarriorCharacteristics getStartWarriorCharacteristics() {
        return startWarriorCharacteristics;
    }

    public DbWarriorCharacteristics getWarriorCharacteristicsByLevel() {
        return warriorCharacteristicsByLevel;
    }
}
