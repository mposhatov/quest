package com.mposhatov.dto;

public class Client {

    private long id;
    private String name;
    private Background photo;
    private long level;
    private long experience;

    public Client(long id, String name, Background photo, long level, long experience) {
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

    public Background getPhoto() {
        return photo;
    }

    public long getLevel() {
        return level;
    }

    public long getExperience() {
        return experience;
    }
}
