package com.mposhatov.dto;

import java.util.List;

public class Quest {

    private long id;
    private String name;
    private String description;
    private Difficulty difficulty;
    private List<Category> categories;
    private boolean passed;

    public Quest(long id, String name, String description, Difficulty difficulty, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.categories = categories;
    }

    public void passed() {
        this.passed = true;
    }

    public long getId() {
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

    public List<Category> getCategories() {
        return categories;
    }

    public boolean isPassed() {
        return passed;
    }
}
