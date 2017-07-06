package com.mposhatov.dto;

import java.util.List;

public class ActiveGame {

    private long id;
    private List<Subject> subjects;
    private List<Event> events;

    public ActiveGame(long id, List<Subject> subjects, List<Event> events) {
        this.id = id;
        this.subjects = subjects;
        this.events = events;
    }

    public long getId() {
        return id;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Event> getEvents() {
        return events;
    }
}
