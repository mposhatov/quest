package com.mposhatov.dto;

import java.util.ArrayList;
import java.util.List;

public class Hero {
    private String name;
    private HeroCharacteristics heroCharacteristics;
    private Inventory inventory;
    private List<Warrior> warriors = new ArrayList<>();
    private Client client;

    public Hero() {
    }

    public Hero(String name, HeroCharacteristics heroCharacteristics, Inventory inventory, List<Warrior> warriors, Client client) {
        this.name = name;
        this.heroCharacteristics = heroCharacteristics;
        this.inventory = inventory;
        this.warriors = warriors;
        this.client = client;
    }

    public String getName() {
        return name;
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

    public Client getClient() {
        return client;
    }
}
