package com.mposhatov.entity;

import javax.persistence.AttributeConverter;

public class AttackTypeConverter implements AttributeConverter<AttackType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AttackType attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public AttackType convertToEntityAttribute(Integer code) {
        return code != null ? AttackType.byCode(code) : null;
    }

}
