package com.mposhatov.dto;

import java.util.List;

public class StepActiveGame {
    private Client firstClient;
    private Client secondClient;
    private List<Warrior> warriors;
    private Warrior currentWarrior;

    private Long attackWarriorId;
    private Long defendWarriorId;

    private boolean gameComplete;

    public StepActiveGame(Client firstClient, Client secondClient, List<Warrior> warriors, Warrior currentWarrior, boolean gameComplete) {
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

    public Long getAttackWarriorId() {
        return attackWarriorId;
    }

    public Long getDefendWarriorId() {
        return defendWarriorId;
    }
}
