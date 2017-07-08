package com.mposhatov.dto;

public class Answer {

    private long id;
    private String description;
    private boolean nextStep;
    private boolean winning;

    public Answer(long id, String description, boolean nextStep, boolean winning) {
        this.id = id;
        this.description = description;
        this.nextStep = nextStep;
        this.winning = winning;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isNextStep() {
        return nextStep;
    }

    public boolean isWinning() {
        return winning;
    }
}
