package com.mposhatov.entity;

import java.util.Arrays;

public enum AttackType {

    PHYSICAL(1),
    MAGICAL(2);

    private int code;

    AttackType(int code) {
        this.code = code;
    }

    public static AttackType byCode(int code) {
        return Arrays.stream(values())
                .filter(o -> o.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + code));
    }

    public int getCode() {
        return code;
    }
}
