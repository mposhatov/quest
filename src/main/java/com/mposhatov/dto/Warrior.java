package com.mposhatov.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mposhatov.entity.RangeType;

public class Warrior {
    private Long id;
    private String name;
    private String pictureName;
    private Integer stage;
    private Integer killedExperience;
    private Boolean main;
    private RangeType rangeType;
    private Hero hero;
    private WarriorCharacteristics warriorCharacteristics;

    public Warrior() {
    }

    public Warrior(Long id, String name, String pictureName, Integer stage, Integer killedExperience, Boolean main, Hero hero, WarriorCharacteristics warriorCharacteristics, RangeType rangeType) {
        this.id = id;
        this.name = name;
        this.pictureName = pictureName;
        this.stage = stage;
        this.killedExperience = killedExperience;
        this.main = main;
        this.hero = hero;
        this.warriorCharacteristics = warriorCharacteristics;
        this.rangeType = rangeType;
    }

    @JsonIgnore
    public boolean isDead() {
        return warriorCharacteristics.getHealth() == 0;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public Boolean getMain() {
        return main;
    }

    public Hero getHero() {
        return hero;
    }

    public WarriorCharacteristics getWarriorCharacteristics() {
        return warriorCharacteristics;
    }

    public RangeType getRangeType() {
        return rangeType;
    }

    @JsonIgnore
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Warrior warrior = (Warrior) o;

        if (id != null ? !id.equals(warrior.id) : warrior.id != null) return false;
        if (name != null ? !name.equals(warrior.name) : warrior.name != null) return false;
        return pictureName != null ? pictureName.equals(warrior.pictureName) : warrior.pictureName == null;
    }

    @JsonIgnore
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pictureName != null ? pictureName.hashCode() : 0);
        return result;
    }
}
