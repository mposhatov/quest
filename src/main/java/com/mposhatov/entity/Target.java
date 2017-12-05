package com.mposhatov.entity;

import java.util.Arrays;

public enum Target {
    ENEMY_ALL_BUSY(1, "All enemies"),
    ENEMY_MELEE_BUSY(2, "Melee enemies"),
    ALLIES_ALL_BUSY(3, "All allies"),

    ALLIES_ALL_FREE(4, "All free allies cards");

    private int code;
    private String title;

    Target(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public static Target byCode(int code) {
        return Arrays.stream(values())
                .filter(rangeType -> rangeType.getCode() == code)
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
