package com.mposhatov.dto;

import java.util.List;

public class QuestFilter {
    private List<Category> categories;
    private List<Difficulty> difficulties;

    public QuestFilter(List<Category> categories, List<Difficulty> difficulties) {
        this.categories = categories;
        this.difficulties = difficulties;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Difficulty> getDifficulties() {
        return difficulties;
    }
}
