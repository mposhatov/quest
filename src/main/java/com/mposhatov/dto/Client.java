package com.mposhatov.dto;

import com.mposhatov.entity.Command;

import java.util.Date;

public class Client {
    private long id;
    private String login;
    private String email;
    private Background photo;
    private Date createdAt;
    private long rate;
    private Hero hero;
    private Command command;

    public Client(long id, String login, String email, Background photo, Date createdAt, long rate, Hero hero) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.photo = photo;
        this.createdAt = createdAt;
        this.rate = rate;
        this.hero = hero;
    }

    public Client setCommand(Command command) {
        this.command = command;
        return this;
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

    public Command getCommand() {
        return command;
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
