package com.mposhatov.dto;

import com.mposhatov.entity.Role;

import java.util.List;

public class ClientSession {

    private Long clientId;
    private List<Role> roles;

    public ClientSession(Long clientId, List<Role> roles) {
        this.clientId = clientId;
        this.roles = roles;
    }

    public Long getClientId() {
        return clientId;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
