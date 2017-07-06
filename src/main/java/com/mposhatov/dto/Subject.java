package com.mposhatov.dto;

public class Subject {

    private long id;
    private String name;
    private String value;
    private long number;

    public Subject(long id, String name, String value, long number) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public long getNumber() {
        return number;
    }
}
