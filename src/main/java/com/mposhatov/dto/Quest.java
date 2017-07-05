package com.mposhatov.dto;

public class Quest {

    private long id;
    private String name;
    private String description;

    public Quest(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}
