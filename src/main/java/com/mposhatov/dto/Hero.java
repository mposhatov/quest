package com.mposhatov.dto;

import java.util.ArrayList;
import java.util.List;

public class Hero {
    private HeroCharacteristics heroCharacteristics;
    private Inventory inventory;
    private List<Warrior> warriors = new ArrayList<>();
    private boolean availableStep;

    public Hero(HeroCharacteristics heroCharacteristics, Inventory inventory, List<Warrior> warriors) {
        this.heroCharacteristics = heroCharacteristics;
        this.inventory = inventory;
        this.warriors = warriors;
        this.availableStep = true;
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
