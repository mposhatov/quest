package com.mposhatov.dto;

import java.util.List;

public class ActiveGame {

    private long id;
    private Quest quest;
    private Step step;
    private List<Subject> subjects;
    private List<Event> events;

    public ActiveGame(long id, Quest quest, Step step, List<Subject> subjects, List<Event> events) {
        this.id = id;
        this.quest = quest;
        this.step = step;
        this.subjects = subjects;
        this.events = events;
    }

    public long getId() {
        return id;
    }

    public Quest getQuest() {
        return quest;
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
