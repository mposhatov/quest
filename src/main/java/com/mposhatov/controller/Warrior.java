package com.mposhatov.controller;

public class Warrior {
    private Long id;
    private Integer position;

    public Warrior() {
    }

    public Warrior(Long id, Integer position) {
        this.id = id;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
