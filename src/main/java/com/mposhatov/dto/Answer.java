package com.mposhatov.dto;

public class Answer {

    private long id;
    private String description;
    private boolean nextStep;

    public Answer(long id, String description, boolean nextStep) {
        this.id = id;
        this.description = description;
        this.nextStep = nextStep;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean getNextStep() {
        return nextStep;
    }
}
