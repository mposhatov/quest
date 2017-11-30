package com.mposhatov.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HERO")
public class DbHero {

    @Id
    @GeneratedValue(generator = "client")
    @GenericGenerator(name = "client", strategy = "foreign", parameters = {@org.hibernate.annotations.Parameter(name = "property", value = "client")})
    @Column(name = "CLIENT_ID")
    private Long clientId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    @Column(name = "EXPERIENCE", nullable = false)
    private Long experience = 0L;

    @Column(name = "LEVEL", nullable = false)
    private Long level = 0L;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "AVAILABLE_CHARACTERISTICS", nullable = false)
    private long availableCharacteristics = 0;

    @Column(name = "AVAILABLE_SKILLS", nullable = false)
    private long availableSkills = 0;

    @Column(name = "AVAILABLE_SLOTS", nullable = false)
    private long availableSlots = 0;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "HERO_CHARACTERISTICS_ID", nullable = true)
    private DbHeroCharacteristics heroCharacteristics;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "hero")
    private DbInventory inventory;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "hero")
    private List<DbWarrior> warriors = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "HERO_HIERARCHY_WARRIOR",
            joinColumns = {@JoinColumn(name = "HERO_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "HIERARCHY_WARRIOR_ID", nullable = false)})
    private List<DbHierarchyWarrior> availableHierarchyWarriors = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "HERO_SPELL_ATTACK",
            joinColumns = {@JoinColumn(name = "HERO_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SPELL_ATTACK_ID", nullable = false)})
    private List<DbSpellAttack> spellAttacks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "HERO_SPELL_HEAL",
            joinColumns = {@JoinColumn(name = "HERO_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SPELL_HEAL_ID", nullable = false)})
    private List<DbSpellHeal> spellHeals = new ArrayList<>();

    protected DbHero() {
    }

    public DbHero(DbClient client, DbAdditionalHeroPoint additionalHeroPoint) {
        this.client = client;
        this.name = "GAMER_" + client.getId();
        upLevel(additionalHeroPoint);
    }

    public DbHero addAvailableWarrior(DbHierarchyWarrior hierarchyWarrior) {
        this.availableHierarchyWarriors.add(hierarchyWarrior);
        return this;
    }

    public DbHero setHeroCharacteristics(DbHeroCharacteristics heroCharacteristics) {
        this.heroCharacteristics = heroCharacteristics;
        return this;
    }

    public DbHero addExperience(Long experience) {
        this.experience += experience;
        return this;
    }

    public DbHero upLevel(DbAdditionalHeroPoint additionalHeroPoint) {
        this.level++;
        CharacteristicsMerge.mapPlusHeroPoints(this, additionalHeroPoint);
        return this;
    }

    public DbHero addSpellAttack(DbSpellAttack spellAttack) {
        this.spellAttacks.add(spellAttack);
        return this;
    }

    public DbHero addSpellAttacks(List<DbSpellAttack> spellAttacks) {
        this.spellAttacks.addAll(spellAttacks);
        return this;
    }

    public DbHero addSpellHeal(DbSpellHeal spellHeal) {
        this.spellHeals.add(spellHeal);
        return this;
    }

    public DbHero addSpellHeals(List<DbSpellHeal> spellHeals) {
        this.spellHeals.addAll(spellHeals);
        return this;
    }

    public DbHero addAvailableCharacteristics(long availableCharacteristics) {
        this.availableCharacteristics += availableCharacteristics;
        return this;
    }

    public DbHero addAvailableSkills(long availableSkills) {
        this.availableSkills += availableSkills;
        return this;
    }

    public DbHero addAvailableSlots(long availableSlots) {
        this.availableSlots += availableSlots;
        return this;
    }

    public DbWarrior addWarrior(DbHierarchyWarrior hierarchyWarrior) {
        final DbWarrior warrior = new DbWarrior(this, hierarchyWarrior);
        this.warriors.add(warrior);
        return warrior;
    }

    public DbHero setName(String name) {
        this.name = name;
        return this;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getExperience() {
        return experience;
    }

    public Long getLevel() {
        return level;
    }

    public long getAvailableCharacteristics() {
        return availableCharacteristics;
    }

    public long getAvailableSkills() {
        return availableSkills;
    }

    public DbHeroCharacteristics getHeroCharacteristics() {
        return heroCharacteristics;
    }

    public DbInventory getInventory() {
        return inventory;
    }

    public List<DbWarrior> getWarriors() {
        return warriors;
    }

    public String getName() {
        return name;
    }

    public DbClient getClient() {
        return client;
    }

    public long getAvailableSlots() {
        return availableSlots;
    }

    public List<DbHierarchyWarrior> getAvailableHierarchyWarriors() {
        return availableHierarchyWarriors;
    }

    public List<DbSpellAttack> getSpellAttacks() {
        return spellAttacks;
    }

    public List<DbSpellHeal> getSpellHeals() {
        return spellHeals;
    }
}
