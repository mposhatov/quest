package com.mposhatov.dto;

import java.util.List;

public class Client {

    private long id;
    private String name;
    private Photo photo;
    private long level;
    private long experience;
    private List<Long> completedQuests;
    private List<Long> notFreeQuests;

    public Client(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(long id, String name, Photo photo, long level, long experience, List<Long> completedQuests, List<Long> notFreeQuests) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.level = level;
        this.experience = experience;
        this.completedQuests = completedQuests;
        this.notFreeQuests = notFreeQuests;
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

    public List<Long> getCompletedQuests() {
        return completedQuests;
    }

    public List<Long> getNotFreeQuests() {
        return notFreeQuests;
    }
}
