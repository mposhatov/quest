package com.mposhatov.dto;

import java.util.ArrayList;
import java.util.List;

public class Hero {
    private String name;
    private HeroCharacteristics heroCharacteristics;
    private Inventory inventory;
    private List<Warrior> warriors = new ArrayList<>();
    private Client client;
    private List<SpellAttack> spellAttacks = new ArrayList<>();
    private List<SpellHeal> spellHeals = new ArrayList<>();
    private List<SpellExhortation> spellExhortations = new ArrayList<>();
    private List<SpellPassive> spellPassives = new ArrayList<>();

    public Hero() {
    }

    public Hero(String name, HeroCharacteristics heroCharacteristics, Inventory inventory, List<Warrior> warriors, Client client,
                List<SpellAttack> spellAttacks, List<SpellHeal> spellHeals, List<SpellExhortation> spellExhortations, List<SpellPassive> spellPassives) {
        this.name = name;
        this.heroCharacteristics = heroCharacteristics;
        this.inventory = inventory;
        this.warriors = warriors;
        this.client = client;
        this.spellAttacks = spellAttacks;
        this.spellHeals = spellHeals;
        this.spellExhortations = spellExhortations;
        this.spellPassives = spellPassives;
    }

    public Hero addWarrior(Warrior warrior) {
        this.warriors.add(warrior);
        return this;
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

    public List<SpellAttack> getSpellAttacks() {
        return spellAttacks;
    }

    public List<SpellHeal> getSpellHeals() {
        return spellHeals;
    }

    public List<SpellExhortation> getSpellExhortations() {
        return spellExhortations;
    }

    public List<SpellPassive> getSpellPassives() {
        return spellPassives;
    }
}
