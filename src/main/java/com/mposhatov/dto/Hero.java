package com.mposhatov.dto;

import java.util.ArrayList;
import java.util.List;

public class Hero {
    private String name;
    private HeroCharacteristics heroCharacteristics;
    private Inventory inventory;
    private List<Warrior> warriors = new ArrayList<>();
    private boolean availableStep;

    public Hero() {
    }

    public Hero(String name, HeroCharacteristics heroCharacteristics, Inventory inventory) {
        this.name = name;
        this.heroCharacteristics = heroCharacteristics;
        this.inventory = inventory;
        this.availableStep = true;
    }

    public Hero setWarriors(List<Warrior> warriors) {
        this.warriors = warriors;
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
}
