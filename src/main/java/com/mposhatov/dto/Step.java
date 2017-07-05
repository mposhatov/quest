package com.mposhatov.dto;

import java.util.List;

public class Step {

    private long id;
    private String description;
    private List<Answer> answers;

    public Step(long id, String description, List<Answer> answers) {
        this.id = id;
        this.description = description;
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
}