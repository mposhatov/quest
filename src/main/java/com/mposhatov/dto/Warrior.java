package com.mposhatov.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Warrior {
    private Long id;
    private String name;
    private String pictureName;
    private Integer level;
    private Integer killedExperience;
    private Integer improvementExperience;
    private Boolean main;
    private Integer position;
    private Hero hero;
    private WarriorCharacteristics warriorCharacteristics;
    private long experience;

    public Warrior() {
    }

    public Warrior(Long id, String name, String pictureName, Integer level, Integer killedExperience, Integer improvementExperience, Boolean main, Integer position, Hero hero, WarriorCharacteristics warriorCharacteristics, long experience) {
        this.id = id;
        this.name = name;
        this.pictureName = pictureName;
        this.level = level;
        this.killedExperience = killedExperience;
        this.improvementExperience = improvementExperience;
        this.main = main;
        this.position = position;
        this.hero = hero;
        this.warriorCharacteristics = warriorCharacteristics;
        this.experience = experience;
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

    public Integer getLevel() {
        return level;
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

    public Integer getPosition() {
        return position;
    }

    public long getExperience() {
        return experience;
    }

    public Integer getImprovementExperience() {
        return improvementExperience;
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
