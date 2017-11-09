package com.mposhatov.controller;

public class Warrior {
    private Long id;
    private boolean main;
    private Integer position;

    public Warrior() {
    }

    public Warrior(Long id, boolean main, Integer position) {
        this.id = id;
        this.main = main;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
