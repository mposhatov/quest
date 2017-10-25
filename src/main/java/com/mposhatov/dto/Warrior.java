package com.mposhatov.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Warrior {
    private Long id;
    private String name;
    private String pictureName;
    private Integer stage;
    private Integer killedExperience;
    private Boolean main;
    private Hero hero;
    private WarriorCharacteristics warriorCharacteristics;

    public Warrior() {
    }

    public Warrior(Long id, String name, String pictureName, Integer stage, Integer killedExperience, Boolean main, Hero hero, WarriorCharacteristics warriorCharacteristics) {
        this.id = id;
        this.name = name;
        this.pictureName = pictureName;
        this.stage = stage;
        this.killedExperience = killedExperience;
        this.main = main;
        this.hero = hero;
        this.warriorCharacteristics = warriorCharacteristics;
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
}
