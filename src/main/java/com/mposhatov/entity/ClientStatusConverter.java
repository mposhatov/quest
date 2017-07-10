package com.mposhatov.entity;

import javax.persistence.AttributeConverter;

public class ClientStatusConverter implements AttributeConverter<ClientStatus, Integer>{

    @Override
    public Integer convertToDatabaseColumn(ClientStatus clientStatus) {
        return clientStatus != null ? clientStatus.getCode() : null;
    }

    @Override
    public ClientStatus convertToEntityAttribute(Integer code) {
        return ClientStatus.byCode(code);
    }
}
