package com.mposhatov.dto;

import java.util.List;

public class ActiveGame {

    private long id;
    private Step step;
    private List<Subject> subjects;
    private List<Event> events;

    public ActiveGame(long id, Step step, List<Subject> subjects, List<Event> events) {
        this.id = id;
        this.step = step;
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

    public Step getStep() {
        return step;
    }
}
