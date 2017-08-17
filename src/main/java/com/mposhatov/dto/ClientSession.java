package com.mposhatov.dto;

import com.mposhatov.entity.Role;

import java.util.List;

public class ClientSession {

    private long clientId;
    private List<Role> roles;

    public ClientSession(long clientId, List<Role> roles) {
        this.clientId = clientId;
        this.roles = roles;
    }

    public boolean isAnonymous() {
        return roles.contains(Role.ROLE_ANONYMOUS);
    }

    public long getClientId() {
        return clientId;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
