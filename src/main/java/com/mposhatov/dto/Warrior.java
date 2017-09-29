package com.mposhatov.dto;

public class Warrior {
    private long id;
    private String name;
    private String pictureName;
    private Command command;
    private WarriorCharacteristics warriorCharacteristics;

    public Warrior(long id, String name, String pictureName, WarriorCharacteristics warriorCharacteristics) {
        this.id = id;
        this.name = name;
        this.pictureName = pictureName;
        this.warriorCharacteristics = warriorCharacteristics;
    }

    public Warrior setCommand(Command command) {
        this.command = command;
        return this;
    }

    public long giveDamage() {
        return this.warriorCharacteristics.getMinDamage();
    }

    public long getId() {
        return id;
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

    public Command getCommand() {
        return command;
    }
}
