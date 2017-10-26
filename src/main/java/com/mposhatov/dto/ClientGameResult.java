package com.mposhatov.dto;

public class ClientGameResult {
    private long id;
    private boolean win;
    private long rating;

    public ClientGameResult(long id, boolean win, long rating) {
        this.id = id;
        this.win = win;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public boolean isWin() {
        return win;
    }

    public long getRating() {
        return rating;
    }
}
