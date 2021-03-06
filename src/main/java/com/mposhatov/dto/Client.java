package com.mposhatov.dto;

import java.util.Date;

public class Client {
    private long id;
    private String login;
    private String email;
    private Background photo;
    private Date createdAt;
    private long rating;
    private Hero hero;

    public Client(long id, String login, String email, Background photo, Date createdAt, long rating, Hero hero) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.photo = photo;
        this.createdAt = createdAt;
        this.rating = rating;
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

    public long getRating() {
        return rating;
    }

    public Hero getHero() {
        return hero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return id == client.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
