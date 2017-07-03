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

    @OneToMany(mappedBy = "dbQuest",fetch = FetchType.LAZY)
    private List<DbStepQuest> steps = new ArrayList<>();

    protected DbQuest() {
    }

    public DbQuest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addStep(DbStepQuest stepQuest) {
        steps.add(stepQuest);
    }

    public void addSteps(Collection<DbStepQuest> stepQuests) {
        stepQuests.addAll(stepQuests);
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

    public List<DbStepQuest> getSteps() {
        return steps;
    }
}
