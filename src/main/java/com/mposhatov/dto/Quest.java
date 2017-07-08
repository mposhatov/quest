package com.mposhatov.dto;

import java.util.Set;

public class Quest {

    private long id;
    private String name;
    private String description;
    private String difficulty;
    private Set<String> categories;

    public Quest(long id, String name, String description, String difficulty, Set<String> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.categories = categories;
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

    public String getDifficulty() {
        return difficulty;
    }

    public Set<String> getCategories() {
        return categories;
    }
}
