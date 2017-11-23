package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "SUBJECT_DESCRIPTION")
public class DbSubjectDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 200, nullable = false)
    private String description;

    @Column(name = "PICTURE_NAME", length = 20, nullable = false)
    private String pictureName;

    @Column(name = "BODY_PART", nullable = false)
    @Convert(converter = BodyPartConverter.class)
    private BodyPart bodyPart;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "HERO_CHARACTERISTICS_ID", nullable = false)
    private DbHeroCharacteristics heroCharacteristics;

    protected DbSubjectDescription() {
    }

    public DbSubjectDescription(String name, String description, String pictureName, BodyPart bodyPart, DbHeroCharacteristics heroCharacteristics) {
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
        this.bodyPart = bodyPart;
        this.heroCharacteristics = heroCharacteristics;
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

    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public DbHeroCharacteristics getHeroCharacteristics() {
        return heroCharacteristics;
    }
}
