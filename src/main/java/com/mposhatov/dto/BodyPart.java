package com.mposhatov.dto;

public class BodyPart {
    private String name;
    private String title;

    public BodyPart(String name, String title) {
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
