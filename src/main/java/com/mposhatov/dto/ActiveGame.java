package com.mposhatov.dto;

import java.util.List;

public class ActiveGame {
    private Client firstClient;
    private Client secondClient;
    private List<Warrior> warriors;
    private Warrior currentWarrior;
    private boolean gameComplete;

    public ActiveGame(Client firstClient, Client secondClient, List<Warrior> warriors, Warrior currentWarrior, boolean gameComplete) {
        this.firstClient = firstClient;
        this.secondClient = secondClient;
        this.warriors = warriors;
        this.currentWarrior = currentWarrior;
        this.gameComplete = gameComplete;
    }

    public Client getFirstClient() {
        return firstClient;
    }

    public Client getSecondClient() {
        return secondClient;
    }

    public List<Warrior> getWarriors() {
        return warriors;
    }

    public Warrior getCurrentWarrior() {
        return currentWarrior;
    }

    public boolean isGameComplete() {
        return gameComplete;
    }
}
