package com.mposhatov.entity;

import java.util.Arrays;

public enum ClientStatus {
    ONLINE(1),
    OFFLINE(2),
    INACTIVE(3);

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
