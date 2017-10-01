package com.mposhatov.dto;

import java.util.Date;

public class Client {
    private long id;
    private String login;
    private String email;
    private Background photo;
    private Date createdAt;
    private long rate;
    private Hero hero;

    public Client() {
    }

    public Client(long id, String login, String email, Background photo, Date createdAt, long rate, Hero hero) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.photo = photo;
        this.createdAt = createdAt;
        this.rate = rate;
        this.hero = hero;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Background getPhoto() {
        return photo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public long getRate() {
        return rate;
    }

    public Hero getHero() {
        return hero;
    }
}
