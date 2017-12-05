package com.mposhatov.dto;

public class Target {

    private String name;
    private String title;

    public Target(String name, String title) {
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
