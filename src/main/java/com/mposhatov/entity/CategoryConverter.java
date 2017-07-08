package com.mposhatov.entity;

import javax.persistence.AttributeConverter;

public class CategoryConverter implements AttributeConverter<Category, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Category category) {
        return category != null ? category.getCode() : null;
    }

    @Override
    public Category convertToEntityAttribute(Integer code) {
        return Category.byCode(code);
    }
}
