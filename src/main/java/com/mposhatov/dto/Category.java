package com.mposhatov.dto;

public class Category {
    private String name;
    private String title;

    public Category(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }
}
