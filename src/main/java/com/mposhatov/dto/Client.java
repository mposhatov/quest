package com.mposhatov.dto;

public class Client {

    private long id;
    private String name;
    private Photo photo;
    private long level;
    private long experience;

    public Client(long id, String name, Photo photo, long level, long experience) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.level = level;
        this.experience = experience;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Photo getPhoto() {
        return photo;
    }

    public long getLevel() {
        return level;
    }

    public long getExperience() {
        return experience;
    }
}
