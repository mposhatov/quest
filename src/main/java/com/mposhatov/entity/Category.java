package com.mposhatov.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Category {
    TRAGEDY(1, "Трагедия"),
    COMEDY(2, "Комедия"),
    DRAMA(3, "Драма"),
    ROMANCE(4, "Роман"),
    WESTERN(5, "Вестерн"),
    ADVENTURE(6, "Приключение"),
    DETECTIVE(7, "Детектив"),
    THRILLER(8, "Триллер"),
    GANGSTER(9, "Гангстерский"),
    FANTASY(10, "Фантастика"),
    HORROR(11, "Ужасы"),
    PSYCHOLOGICAL(12, "Психологический"),
    LOGICAL(13, "Логический");

    private int code;
    private String title;

    Category(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public static Category byCode(int code) {
        Optional<Category> first = Arrays.stream(values()).filter(o -> o.getCode() == code).findFirst();
        if(first.isPresent()) {
            return first.get();
        }
        else {
            throw new IllegalArgumentException("Unknown code: " + code);
        }
    }

    public int getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }
}
