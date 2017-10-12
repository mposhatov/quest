package com.mposhatov.dto;

import com.mposhatov.entity.Command;

import java.util.ArrayList;
import java.util.List;

public class Hero {
    private String name;
    private HeroCharacteristics heroCharacteristics;
    private Inventory inventory;
    private List<Warrior> warriors = new ArrayList<>();
    private boolean availableStep;
    private Command command;

    public Hero() {
    }

    public Hero(String name, HeroCharacteristics heroCharacteristics, Inventory inventory, List<Warrior> warriors) {
        this.name = name;
        this.heroCharacteristics = heroCharacteristics;
        this.inventory = inventory;
        this.warriors = warriors;
    }

    public Hero setWarriors(List<Warrior> warriors) {
        this.warriors = warriors;
        return this;
    }

    public Hero setCommand(Command command) {
        this.command = command;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setAvailableStep(boolean availableStep) {
        this.availableStep = availableStep;
    }

    public HeroCharacteristics getHeroCharacteristics() {
        return heroCharacteristics;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Warrior> getWarriors() {
        return warriors;
    }

    public boolean isAvailableStep() {
        return availableStep;
    }

    public Command getCommand() {
        return command;
    }
}
