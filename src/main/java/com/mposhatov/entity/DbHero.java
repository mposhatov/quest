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
    protected long experience = 0;

    @Column(name = "LEVEL", nullable = false)
    protected long level = 1;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "AVAILABLE_CHARACTERISTICS", nullable = false)
    private long availableCharacteristics = 2;

    @Column(name = "AVAILABLE_SKILLS", nullable = false)
    private long availableSkills = 1;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "HERO_CHARACTERISTICS_ID", nullable = false)
    private DbHeroCharacteristics heroCharacteristics;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "hero")
//    @JoinColumn(name = "INVENTORY_ID", nullable = false)
    private DbInventory inventory;

    @Column(name = "AVAILABLE_SLOTS", nullable = false)
    private long availableSlots = 7;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "hero")
    private List<DbWarrior> warriors = new ArrayList<>();

    protected DbHero() {
    }

    public DbHero(DbClient client) {
        this.client = client;
        this.name = "GAMER_" + client.getId();
        this.heroCharacteristics = new DbHeroCharacteristics();
    }

    public DbHero upLevel() {
        this.level++;
        addAvailableCharacteristicsByLevel();
        addAvailableSkillsByLevel();
        return this;
    }

    private DbHero addAvailableCharacteristicsByLevel() {
        this.availableCharacteristics += 2;
        return this;
    }


    public DbHero minusAvailableCharacteristics(long availableCharacteristics) {
        this.availableCharacteristics -= availableCharacteristics;
        return this;
    }

    private DbHero addAvailableSkillsByLevel() {
        this.availableSkills += 1;
        return this;
    }

    public DbHero minusAvailableSkills(long availableSkills) {
        this.availableSkills -= availableSkills;
        return this;
    }

    public DbHero addWarrior(DbWarrior warrior) {
        this.warriors.add(warrior);
        if (this.availableSlots > 0) {
            warrior.setMain();
            this.availableSlots--;
        }
        return this;
    }

    public DbHero setName(String name) {
        this.name = name;
        return this;
    }

    public Long getClientId() {
        return clientId;
    }

    public long getExperience() {
        return experience;
    }

    public long getLevel() {
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
}
