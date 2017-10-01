package com.mposhatov.dto;

public class Subject {
    private long id;
    private String name;
    private String description;
    private String pictureName;
    private BodyPart bodyPart;
    private boolean main;

    public Subject(long id, String name, String description, String pictureName, BodyPart bodyPart, boolean main) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pictureName = pictureName;
        this.bodyPart = bodyPart;
        this.main = main;
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

    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public boolean isMain() {
        return main;
    }
}
