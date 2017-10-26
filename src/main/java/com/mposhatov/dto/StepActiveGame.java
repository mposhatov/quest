package com.mposhatov.dto;

import java.util.Deque;

public class StepActiveGame {
    private Client firstClient;
    private Client secondClient;
    private Deque<Warrior> warriors;
    private Warrior currentWarrior;

    private Long attackWarriorId;
    private Long defendWarriorId;

    private boolean gameComplete;
    private Long closedGameId;

    public StepActiveGame(Client firstClient, Client secondClient, Deque<Warrior> warriors, Warrior currentWarrior, boolean gameComplete) {
        this.firstClient = firstClient;
        this.secondClient = secondClient;
        this.warriors = warriors;
        this.currentWarrior = currentWarrior;
        this.gameComplete = gameComplete;
    }

    public StepActiveGame setAttackWarriorId(Long warriorId) {
        this.attackWarriorId = warriorId;
        return this;
    }

    public StepActiveGame setDefendWarriorId(Long warriorId) {
        this.defendWarriorId = warriorId;
        return this;
    }

    public StepActiveGame setClosedGameId(Long closedGameId) {
        this.closedGameId = closedGameId;
        return this;
    }

    public Client getFirstClient() {
        return firstClient;
    }

    public Client getSecondClient() {
        return secondClient;
    }

    public Deque<Warrior> getWarriors() {
        return warriors;
    }

    public Warrior getCurrentWarrior() {
        return currentWarrior;
    }

    public boolean isGameComplete() {
        return gameComplete;
    }

    public Long getAttackWarriorId() {
        return attackWarriorId;
    }

    public Long getDefendWarriorId() {
        return defendWarriorId;
    }

    public Long getClosedGameId() {
        return closedGameId;
    }
}
