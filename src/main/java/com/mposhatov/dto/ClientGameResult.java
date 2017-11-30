package com.mposhatov.dto;

import java.util.ArrayList;
import java.util.List;

public class ClientGameResult {
    private long clientId;
    private boolean win;
    private long rating;
    private List<WarriorUpgrade> warriorUpgrades = new ArrayList<>();

    public ClientGameResult() {
    }

    public ClientGameResult(long clientId, boolean win, long rating) {
        this.clientId = clientId;
        this.win = win;
        this.rating = rating;
    }

    public ClientGameResult(long clientId, boolean win, long rating, List<WarriorUpgrade> warriorUpgrades) {
        this.clientId = clientId;
        this.win = win;
        this.rating = rating;
    }

    public ClientGameResult clientId(long clientId) {
        this.clientId = clientId;
        return this;
    }

    public ClientGameResult win() {
        this.win = true;
        return this;
    }

    public ClientGameResult rating(long rating) {
        this.rating = rating;
        return this;
    }

    public ClientGameResult warriorUpgrades(List<WarriorUpgrade> warriorUpgrades) {
        this.warriorUpgrades = warriorUpgrades;
        return this;
    }

    public long getClientId() {
        return clientId;
    }

    public boolean isWin() {
        return win;
    }

    public long getRating() {
        return rating;
    }

    public List<WarriorUpgrade> getWarriorUpgrades() {
        return warriorUpgrades;
    }
}
