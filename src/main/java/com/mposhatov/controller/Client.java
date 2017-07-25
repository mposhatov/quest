package com.mposhatov.controller;

import com.mposhatov.entity.Role;

import java.util.List;

public class Client {

    private long id;
    private List<Role> roles;

    public Client(long id, List<Role> roles) {
        this.id = id;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public boolean isGuest() {
        return roles.contains(Role.ROLE_ANONYMOUS);
    }
}
