package com.mposhatov.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mposhatov.entity.Category;
import com.mposhatov.entity.Difficulty;

import java.util.List;

class QuestFilter {

    @JsonProperty("page")
    private int page;

    @JsonProperty("categories")
    private List<Category> categories;

    @JsonProperty("difficulties")
    private List<Difficulty> difficulties;

    public QuestFilter() {
    }

    public QuestFilter(int page, List<Category> categories, List<Difficulty> difficulties) {
        this.page = page;
        this.categories = categories;
        this.difficulties = difficulties;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Difficulty> getDifficulties() {
        return difficulties;
    }

    public void setDifficulties(List<Difficulty> difficulties) {
        this.difficulties = difficulties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestFilter that = (QuestFilter) o;

        if (page != that.page) return false;
        if (categories != null ? !categories.equals(that.categories) : that.categories != null) return false;
        return difficulties != null ? difficulties.equals(that.difficulties) : that.difficulties == null;
    }

    @Override
    public int hashCode() {
        int result = page;
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        result = 31 * result + (difficulties != null ? difficulties.hashCode() : 0);
        return result;
    }
}
