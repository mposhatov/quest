package com.mposhatov.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Warrior {
    private Long id;
    private String name;
    private String pictureName;
    private Integer level;
    private Long killedExperience;
    private Long improvementExperience;
    private Boolean main;
    private Integer position;
    private Hero hero;
    private WarriorCharacteristics warriorCharacteristics;
    private Long experience;
    private List<SpellAttack> spellAttacks = new ArrayList<>();

    public Warrior() {
    }

    public Warrior(Long id, String name, String pictureName, Integer level, Long killedExperience, Long improvementExperience, Boolean main, Integer position, Hero hero, WarriorCharacteristics warriorCharacteristics, long experience, List<SpellAttack> spellAttacks) {
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
        this.spellAttacks = spellAttacks;
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

    public Long getKilledExperience() {
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

    public Long getExperience() {
        return experience;
    }

    public Long getImprovementExperience() {
        return improvementExperience;
    }

    public List<SpellAttack> getSpellAttacks() {
        return spellAttacks;
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
