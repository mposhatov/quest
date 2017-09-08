package com.mposhatov.dto;

public class Client {
    private long rate;
    private Background background;
    private Hero hero;

    public Client(long rate, Background background, Hero hero) {
        this.rate = rate;
        this.background = background;
        this.hero = hero;
    }

    public long getRate() {
        return rate;
    }

    public Background getBackground() {
        return background;
    }

    public Hero getHero() {
        return hero;
    }
}
