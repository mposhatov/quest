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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HERO_ID", nullable = false)
    private DbHero hero;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "HIERARCHY_WARRIOR_ID", nullable = false)
    private DbHierarchyWarrior hierarchyWarrior;

    @Column(name = "MAIN", nullable = false)
    private boolean main = false;

    @Column(name = "POSITION", nullable = true)
    private Integer position = null;

    protected DbWarrior() {
    }

    public DbWarrior(DbHero hero, DbHierarchyWarrior hierarchyWarrior) {
        this.hero = hero;
        this.hierarchyWarrior = hierarchyWarrior;
    }

    public DbWarrior hierarchyWarrior(DbHierarchyWarrior hierarchyWarrior) {
        this.hierarchyWarrior = hierarchyWarrior;
        this.experience = 0;
        return this;
    }

    public DbWarrior addExperience(Long experience) {
        this.experience += experience;
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

    public DbHero getHero() {
        return hero;
    }

    public DbHierarchyWarrior getHierarchyWarrior() {
        return hierarchyWarrior;
    }

    public boolean isMain() {
        return main;
    }

    public Integer getPosition() {
        return position;
    }
}
