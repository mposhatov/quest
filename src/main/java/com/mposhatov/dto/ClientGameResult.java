package com.mposhatov.dto;

public class ClientGameResult {
    private long id;
    private Client client;
    private boolean win;
    private long rating;

    public ClientGameResult(long id, Client client, boolean win, long rating) {
        this.id = id;
        this.client = client;
        this.win = win;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public boolean isWin() {
        return win;
    }

    public long getRating() {
        return rating;
    }
}
