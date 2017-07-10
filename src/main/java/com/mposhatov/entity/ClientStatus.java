package com.mposhatov.entity;

import java.util.Arrays;
import java.util.Optional;

public enum ClientStatus {
    ONLINE(1),
    OFFLINE(2),
    INACTIVE(3);

    private int code;

    ClientStatus(int code) {
        this.code = code;
    }

    public static ClientStatus byCode(int code){
        final Optional<ClientStatus> first = Arrays.stream(values()).filter(o -> o.getCode() == code).findFirst();
        if(first.isPresent()) {
            return first.get();
        }
        else {
            throw new IllegalArgumentException("Unknown code: " + code);
        }
    }

    public int getCode() {
        return code;
    }
}
