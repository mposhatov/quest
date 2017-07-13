package com.mposhatov.dto;

public class Difficulty {
    private String name;
    private String title;

    public Difficulty(String name, String title) {
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
