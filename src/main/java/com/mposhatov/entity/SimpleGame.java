package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SIMPLE_GAME")
public class SimpleGame {

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

    @Column(name = "APPROVED", nullable = false)
    private boolean approved;

    @Column(name = "RATING", nullable = false)
    private float rating;

    @Column(name = "PICTURE_NAME", nullable = false)
    private String pictureName;

    @Column(name = "GIVING_EXPERIENCE", nullable = false)
    private long givingExperience;

    @Column(name = "GIVING_GOLDEN_COINS", nullable = false)
    private long givingGoldenCoins;

    @Column(name = "GIVING_DIAMONDS", nullable = false)
    private long givingDiamonds;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "START_STEP_ID", nullable = false)
    private DbStep startStep;

    @ElementCollection(targetClass = Category.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "GAME_CATEGORY", joinColumns = @JoinColumn(name = "SIMPLE_GAME_ID", nullable = false))
    @Column(name = "CATEGORY")
    @Convert(converter = CategoryConverter.class)
    private List<Category> categories = new ArrayList<>();

    protected SimpleGame() {
    }

    public SimpleGame addCategory(Category category) {
        this.categories.add(category);
        return this;
    }

    public SimpleGame addCategories(Collection<Category> categories) {
        this.categories.addAll(categories);
        return this;
    }

    public SimpleGame approve() {
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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isApproved() {
        return approved;
    }

    public float getRating() {
        return rating;
    }

    public String getPictureName() {
        return pictureName;
    }

    public long getGivingExperience() {
        return givingExperience;
    }

    public long getGivingGoldenCoins() {
        return givingGoldenCoins;
    }

    public long getGivingDiamonds() {
        return givingDiamonds;
    }

    public DbStep getStartStep() {
        return startStep;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
