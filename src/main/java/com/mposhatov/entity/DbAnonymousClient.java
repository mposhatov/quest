package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "ANONYMOUS_CLIENT")
public class DbAnonymousClient extends Client {

    @Column(name = "JSESSIONID", nullable = false)
    private String jsessionId;

    protected DbAnonymousClient() {
    }

    public DbAnonymousClient(String jsessionId) {
        this.jsessionId = jsessionId;
    }

    public String getJsessionId() {
        return jsessionId;
    }
}
