package com.mposhatov.entity;

import javax.persistence.AttributeConverter;

public class DifficultyConverter implements AttributeConverter<Difficulty, Integer>{

    @Override
    public Integer convertToDatabaseColumn(Difficulty difficulty) {
        return difficulty != null ? difficulty.getCode() : null;
    }

    @Override
    public Difficulty convertToEntityAttribute(Integer code) {
        return Difficulty.byCode(code);
    }
}
