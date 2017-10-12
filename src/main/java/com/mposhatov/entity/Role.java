package com.mposhatov.entity;

import java.util.Arrays;

public enum Role {
    ROLE_ADMIN(1, "/admin"),
    ROLE_GAMER(2, "/profile"),
    ROLE_GUEST(3, "/welcome");

    private int code;
    private String homePage;

    Role(int code, String homePage) {
        this.code = code;
        this.homePage = homePage;
    }

    public static Role byCode(int code) {
        return Arrays.stream(values())
                .filter(role -> role.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + code));
    }

    public int getCode() {
        return code;
    }

    public String getHomePage() {
        return homePage;
    }
}
