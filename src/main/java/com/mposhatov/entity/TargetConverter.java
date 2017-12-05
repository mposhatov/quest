package com.mposhatov.entity;


import javax.persistence.AttributeConverter;

public class TargetConverter implements AttributeConverter<Target, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Target target) {
        return target != null ? target.getCode() : null;
    }

    @Override
    public Target convertToEntityAttribute(Integer code) {
        return code != null ? Target.byCode(code) : null;
    }
}
