package com.mposhatov.dto;

import java.util.List;

public class Step {

    private long id;
    private String description;
    private Background background;
    private List<Answer> answers;

    public Step(long id, String description, Background background) {
        this.id = id;
        this.description = description;
        this.background = background;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Background getBackground() {
        return background;
    }
}
