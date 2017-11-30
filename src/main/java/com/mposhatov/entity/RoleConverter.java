package com.mposhatov.entity;

import javax.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return role != null ? role.getCode() : null;
    }

    @Override
    public Role convertToEntityAttribute(Integer code) {
        return code != null ? Role.byCode(code) : null;
    }
}
