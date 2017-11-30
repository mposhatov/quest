package com.mposhatov.entity;

import java.util.Arrays;

public enum BodyPart {
    HEAD(1, "Голова"),
    NECK(2, "Шея"),
    SHOULDERS(3, "Плечи"),
    TORSO(4, "Тело"),
    RIGHT_HAND(5, "Оружие"),
    LEFT_HAND(6, "Шит"),
    FINGER(7, "Кольцо"),
    LEGS(8, "Ноги");

    private int code;
    private String title;

    BodyPart(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public static BodyPart byCode(int code) {
        return Arrays.stream(values())
                .filter(role -> role.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + code));
    }

    public int getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }
}
