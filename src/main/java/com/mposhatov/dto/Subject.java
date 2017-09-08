package com.mposhatov.dto;

public class Subject {

    private long id;
    private String name;
    private String description;
    private String pictureName;

    public Subject(long id, String name, String description, String pictureName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
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

    public String getPictureName() {
        return pictureName;
    }
}
