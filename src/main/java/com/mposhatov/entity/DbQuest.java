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

    //todo nullable=false
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "START_STEP_ID", nullable = true)
    private DbStep startStep;

    @OneToMany(mappedBy = "dbQuest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DbStep> steps = new ArrayList<>();

    protected DbQuest() {
    }

    //Create free quest
    public DbQuest(String name, String description) {
        this.name = name;
        this.description = description;
        this.approved = false;
        this.free = true;
    }

    //Create not free quest
    public DbQuest(String name, String description, float costUSD) {
        this.name = name;
        this.description = description;
        this.costUSD = costUSD;
        this.approved = false;
        this.free = false;
    }

    public void addStep(DbStep step) {
        this.steps.add(step);
    }

    public void addSteps(Collection<DbStep> steps) {
        this.steps.addAll(steps);
    }

    public void setStartStep(DbStep startStep) {
        this.startStep = startStep;
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

    public DbStep getStartStep() {
        return startStep;
    }

    public List<DbStep> getSteps() {
        return steps;
    }
}
