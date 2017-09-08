package com.mposhatov.dto;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Subject> subjects = new ArrayList<>();

    public Inventory(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
