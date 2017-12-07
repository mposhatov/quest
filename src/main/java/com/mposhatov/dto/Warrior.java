package com.mposhatov.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mposhatov.entity.CharacteristicsMerge;

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
    private List<SpellHeal> spellHeals = new ArrayList<>();
    private List<SpellExhortation> spellExhortations = new ArrayList<>();
    private List<SpellPassive> spellPassives = new ArrayList<>();
    private List<Effect> effects = new ArrayList<>();

    public Warrior() {
    }

    public Warrior(Long id, String name, String pictureName, Integer level, Long killedExperience, Long improvementExperience, Boolean main, Integer position, Hero hero, WarriorCharacteristics warriorCharacteristics, long experience, List<SpellAttack> spellAttacks, List<SpellHeal> spellHeals, List<SpellExhortation> spellExhortations, List<SpellPassive> spellPassives) {
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
        this.spellHeals = spellHeals;
        this.spellExhortations = spellExhortations;
        this.spellPassives = spellPassives;
    }

    @JsonIgnore
    public Warrior addEffect(Effect effect) {

        this.effects.add(effect);
        CharacteristicsMerge.mapPlusWarriorCharacteristics(this.warriorCharacteristics, effect.getCharacteristics());

        return this;
    }

    @JsonIgnore
    public Warrior refreshEffect(String name) {

        for (Effect effect : this.effects) {
            if (effect.getName().equals(name)) {
                effect.refresh();
            }
        }

        return this;
    }

    @JsonIgnore
    public Warrior deleteEffect(Effect effect) {
        CharacteristicsMerge.mapMinusWarriorCharacteristics(this.warriorCharacteristics, effect.getCharacteristics());
        this.effects.remove(effect);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<SpellHeal> getSpellHeals() {
        return spellHeals;
    }

    public List<SpellExhortation> getSpellExhortations() {
        return spellExhortations;
    }

    public List<SpellPassive> getSpellPassives() {
        return spellPassives;
    }

    public List<Effect> getEffects() {
        return effects;
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
