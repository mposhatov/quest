package com.mposhatov.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Level {
    LEVEL_1(1, 0),
    LEVEL_2(2, 100),
    LEVEL_3(3, 300),
    LEVEL_4(4, 700),
    LEVEL_5(5, 1500),
    LEVEL_6(6, 3100);

    private long code;
    private long experienceToThisLevel;

    Level(long code, long experienceToThisLevel) {
        this.code = code;
        this.experienceToThisLevel = experienceToThisLevel;
    }

    public static Level byCode(long code){
        final Optional<Level> first = Arrays.stream(values()).filter(role -> role.getCode() == code).findFirst();
        if(first.isPresent()) {
            return first.get();
        }
        else {
            throw new IllegalArgumentException("Unknown code: " + code);
        }
    }

    public long getCode() {
        return code;
    }

    public long getExperienceToThisLevel() {
        return experienceToThisLevel;
    }

    public long getExperienceToNextLevel() {
        return Level.byCode(this.getCode() + 1).getExperienceToThisLevel();
    }
}
