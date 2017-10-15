package com.mposhatov.dto;

import com.mposhatov.entity.Command;

public class Warrior {
    private long id;
    private String name;
    private String pictureName;
    private boolean main;
    private Command command;
    private WarriorCharacteristics warriorCharacteristics;

    public Warrior() {
    }

    public Warrior(long id, String name, String pictureName, boolean main, WarriorCharacteristics warriorCharacteristics) {
        this.id = id;
        this.name = name;
        this.pictureName = pictureName;
        this.main = main;
        this.warriorCharacteristics = warriorCharacteristics;
    }

    public Warrior setCommand(Command command) {
        this.command = command;
        return this;
    }

    public boolean isDead() {
        return warriorCharacteristics.getHealth() == 0;
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

    public boolean isMain() {
        return main;
    }
}
