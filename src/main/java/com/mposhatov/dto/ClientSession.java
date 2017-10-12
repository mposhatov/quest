package com.mposhatov.dto;

import com.mposhatov.entity.Role;

import java.util.List;

public class ClientSession {

    private long clientId;
    private long heroId;
    private List<Role> roles;

    public ClientSession(long clientId, long heroId, List<Role> roles) {
        this.clientId = clientId;
        this.heroId = heroId;
        this.roles = roles;
    }

    public long getClientId() {
        return clientId;
    }

    public long getHeroId() {
        return heroId;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
