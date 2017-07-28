package com.mposhatov.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

    @Convert(converter = DifficultyConverter.class)
    @Column(name = "DIFFICULTY", nullable = false)
    private Difficulty difficulty;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    @Column(name = "EXPERIENCE", nullable = false)
    private long experience;

    @Column(name = "APPROVED", nullable = false)
    private boolean approved;

    @Column(name = "FREE", nullable = false)
    private boolean free;

    @Column(name = "COST_USD", nullable = true)
    private float costUSD;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "START_STEP_ID", nullable = false)//todo nullable false
    private DbStep startStep;

    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "QUESTS_CATEGORY", joinColumns = @JoinColumn(name = "QUEST_ID", nullable = false))
    @Column(name = "CATEGORY")
    @Convert(converter = CategoryConverter.class)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "quest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DbStep> steps = new ArrayList<>();

    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @OneToMany(mappedBy = "quest", fetch = FetchType.LAZY)
    private List<DbBackground> backgrounds = new ArrayList<>();

    protected DbQuest() {
    }

    //Create free quest
    public DbQuest(String name, String description, Difficulty difficulty, long experience, List<Category> categories) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.difficulty = difficulty;
        this.createdAt = new Date();
        this.experience = experience;
        this.approved = false;
        this.free = true;
    }

    //Create not free quest
    public DbQuest(String name, String description, float costUSD, Difficulty difficulty, long experience, List<Category> categories) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.experience = experience;
        this.createdAt = new Date();
        this.costUSD = costUSD;
        this.categories = categories;
        this.approved = false;
        this.free = false;
    }

    public void setStartStep(DbStep startStep) {
        this.startStep = startStep;
    }

    public DbQuest addCategory(Category category) {
        this.categories.add(category);
        return this;
    }

    public DbQuest addCategories(Collection<Category> categories) {
        this.categories.addAll(categories);
        return this;
    }

    public DbQuest addStep(DbStep step) {
        this.steps.add(step);
        return this;
    }

    public DbQuest addSteps(Collection<DbStep> steps) {
        this.steps.addAll(steps);
        return this;
    }

    public DbQuest approve() {
        this.approved = true;
        return this;
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

    public List<DbBackground> getBackgrounds() {
        return backgrounds;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public long getExperience() {
        return experience;
    }
}
