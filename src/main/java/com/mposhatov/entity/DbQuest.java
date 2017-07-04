package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "QUEST")
public class DbQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 1000, nullable = false)
    private String description;

    @Column(name = "APPROVED", nullable = false)
    private boolean approved;

    @Column(name = "FREE", nullable = false)
    private boolean free;

    @Column(name = "COST_USD", nullable = true)
    private float costUSD;

    @OneToMany(mappedBy = "dbQuest", fetch = FetchType.LAZY)
    private List<DbStep> steps = new ArrayList<>();

    public DbQuest() {
    }

    public DbQuest(String name, String description, boolean approved, boolean free) {
        this.name = name;
        this.description = description;
        this.approved = approved;
        this.free = free;
    }

    public DbQuest(String name, String description, boolean approved, boolean free, float costUSD) {
        this.name = name;
        this.description = description;
        this.approved = approved;
        this.free = free;
        this.costUSD = costUSD;
    }

    public void addStep(DbStep step) {
        steps.add(step);
    }

    public void addSteps(Collection<DbStep> steps) {
        steps.addAll(steps);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isApproved() {
        return approved;
    }

    public boolean isFree() {
        return free;
    }

    public float getCostUSD() {
        return costUSD;
    }

    public List<DbStep> getSteps() {
        return steps;
    }
}
