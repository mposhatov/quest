package com.mposhatov.entity;

import java.util.Arrays;

public enum RangeType {
    MELEE(1),
    RANGE(2);

    private int code;

    RangeType(int code) {
        this.code = code;
    }

    public static RangeType byCode(int code) {
        return Arrays.stream(values())
                .filter(rangeType -> rangeType.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + code));
    }

    public int getCode() {
        return code;
    }
}
