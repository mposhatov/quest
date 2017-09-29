package com.mposhatov.entity;

import java.util.Arrays;

public enum SpellDuration {

    LONG_LIVED(1),
    NO_LIVED(2);

    private int code;

    SpellDuration(int code) {
        this.code = code;
    }

    public static SpellDuration byCode(int code) {
        return Arrays.stream(values())
                .filter(o -> o.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + code));
    }

    public int getCode() {
        return code;
    }

}
