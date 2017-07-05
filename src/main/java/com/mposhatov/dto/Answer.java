package com.mposhatov.dto;

public class Answer {

    private long id;
    private String description;

    public Answer(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
