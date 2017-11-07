package com.mposhatov.entity;


import javax.persistence.AttributeConverter;

public class RangeTypeConverter implements AttributeConverter<RangeType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RangeType rangeType) {
        return rangeType != null ? rangeType.getCode() : null;
    }

    @Override
    public RangeType convertToEntityAttribute(Integer code) {
        return code != null ? RangeType.byCode(code) : null;
    }
}
