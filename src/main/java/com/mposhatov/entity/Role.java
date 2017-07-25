package com.mposhatov.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    ROLE_GAMER(1, "/main"),
    ROLE_ADMIN(2, "/admin"),
    ROLE_AUTHOR(3, "/author"),
    ROLE_ANONYMOUS(4, "/home?error=true");

    private int code;
    private String homePage;

    Role(int code, String homePage) {
        this.code = code;
        this.homePage = homePage;
    }

    public static Role byCode(int code){
        final Optional<Role> first = Arrays.stream(values()).filter(role -> role.getCode() == code).findFirst();
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

    public String getHomePage() {
        return homePage;
    }
}
