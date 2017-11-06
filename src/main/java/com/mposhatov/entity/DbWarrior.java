package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "WARRIOR")
public class DbWarrior {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "EXPERIENCE", nullable = false)
    protected long experience = 0;

    @Column(name = "LEVEL", nullable = false)
    protected long level = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HERO_ID", nullable = false)
    private DbHero hero;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "WARRIOR_CHARACTERISTICS_ID", nullable = true)
    private DbWarriorCharacteristics warriorCharacteristics;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "WARRIOR_DESCRIPTION_ID", nullable = false)
    private DbWarriorDescription warriorDescription;

    @Column(name = "MAIN", nullable = false)
    private boolean main = false;

    @Column(name = "POSITION", nullable = true)
    private Integer position;

    protected DbWarrior() {
        super();
    }

    public DbWarrior(DbHero hero, DbWarriorDescription warriorDescription) {
        this.hero = hero;
        this.warriorDescription = warriorDescription;
    }

    public DbWarrior setWarriorCharacteristics(DbWarriorCharacteristics warriorCharacteristics) {
        this.warriorCharacteristics = warriorCharacteristics;
        return this;
    }

    public DbWarrior addExperience(Long experience) {
        this.experience += experience;
        return this;
    }

    public DbWarrior upLevel(DbAdditionalWarriorCharacteristics additionalWarriorCharacteristics) {
        this.level++;
        CharacteristicsMerge.mapPlusWarriorCharacteristics(this.warriorCharacteristics, additionalWarriorCharacteristics);
        return this;
    }

    public DbWarrior setMain(Integer position) {
        this.main = true;
        this.position = position;
        return this;
    }

    public DbWarrior setNoMain() {
        this.main = false;
        this.position = null;
        return this;
    }

    public Long getId() {
        return id;
    }

    public long getExperience() {
        return experience;
    }

    public long getLevel() {
        return level;
    }

    public DbHero getHero() {
        return hero;
    }

    public DbWarriorDescription getWarriorDescription() {
        return warriorDescription;
    }

    public DbWarriorCharacteristics getWarriorCharacteristics() {
        return warriorCharacteristics;
    }

    public boolean isMain() {
        return main;
    }

    public Integer getPosition() {
        return position;
    }
}
