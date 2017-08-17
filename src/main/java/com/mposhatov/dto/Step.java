package com.mposhatov.dto;

import java.util.List;

public class Step {

    private long id;
    private String description;
    private String backgroundName;
    private List<Answer> answers;

    public Step(long id, String description, String backgroundName) {
        this.id = id;
        this.description = description;
        this.backgroundName = backgroundName;
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

    public String getBackgroundName() {
        return backgroundName;
    }
}
