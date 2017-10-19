package com.mposhatov.dto;

public class ClientGameResult {
    private Client client;
    private boolean win;
    private long rating;

    public ClientGameResult(Client client, boolean win, long rating) {
        this.client = client;
        this.win = win;
        this.rating = rating;
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
