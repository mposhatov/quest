package com.mposhatov.entity;

import java.util.Arrays;

public enum ClientStatus {
    ACTIVE(1),
    INACTIVE(2);

    private int code;

    ClientStatus(int code) {
        this.code = code;
    }

    public static ClientStatus byCode(int code) {
        return Arrays.stream(values())
                .filter(o -> o.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + code));
    }

    public int getCode() {
        return code;
    }
}
