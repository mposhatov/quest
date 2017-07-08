package com.mposhatov.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Difficulty {
    EASY(1, "Легко"),
    MEDIUM(2, "Средне"),
    HARD(3, "Тяжело");

    private int code;
    private String title;

    Difficulty(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public static Difficulty byCode(int code){
        final Optional<Difficulty> first = Arrays.stream(values()).filter(o -> o.getCode() == code).findFirst();
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
