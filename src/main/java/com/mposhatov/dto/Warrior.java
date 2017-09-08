package com.mposhatov.dto;

public class Warrior {
    private String name;
    private String pictureName;
    private WarriorCharacteristics warriorCharacteristics;

    public Warrior(String name, String pictureName, WarriorCharacteristics warriorCharacteristics) {
        this.name = name;
        this.pictureName = pictureName;
        this.warriorCharacteristics = warriorCharacteristics;
    }

    public String getName() {
        return name;
    }

    public String getPictureName() {
        return pictureName;
    }

    public WarriorCharacteristics getWarriorCharacteristics() {
        return warriorCharacteristics;
    }
}
