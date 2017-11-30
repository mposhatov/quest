package com.mposhatov.entity;

import javax.persistence.AttributeConverter;

public class BodyPartConverter implements AttributeConverter<BodyPart, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BodyPart bodyPart) {
        return bodyPart != null ? bodyPart.getCode() : null;
    }

    @Override
    public BodyPart convertToEntityAttribute(Integer code) {
        return code != null ? BodyPart.byCode(code) : null;
    }
}
