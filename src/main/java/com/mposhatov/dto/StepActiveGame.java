package com.mposhatov.dto;

import java.util.Deque;

public class StepActiveGame {
    private Client me;
    private Client anotherClient;
    private Deque<Warrior> warriors;
    private Warrior currentWarrior;

    private Long attackWarriorId;
    private Long defendWarriorId;

    private boolean gameComplete;
    private ClientGameResult myClientGameResult;
    private ClientGameResult anotherClientGameResult;

    public StepActiveGame(Client me, Client anotherClient, Deque<Warrior> warriors, Warrior currentWarrior, boolean gameComplete) {
        this.me = me;
        this.anotherClient = anotherClient;
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

    public StepActiveGame myClientGameResult(ClientGameResult myClientGameResult) {
        this.myClientGameResult = myClientGameResult;
        return this;
    }

    public StepActiveGame anotherClientGameResult(ClientGameResult anotherClientGameResult) {
        this.anotherClientGameResult = anotherClientGameResult;
        return this;
    }

    public Client getMe() {
        return me;
    }

    public Client getAnotherClient() {
        return anotherClient;
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

    public ClientGameResult getMyClientGameResult() {
        return myClientGameResult;
    }

    public ClientGameResult getAnotherClientGameResult() {
        return anotherClientGameResult;
    }
}
