package com.mposhatov.dto;

import java.util.Deque;

public class StepActiveGame {
    private Client me;
    private Client anotherClient;
    private Deque<Warrior> warriors;
    private Warrior currentWarrior;

    private Long attackWarriorId;
    private Long defendWarriorId;

    private boolean gameOver;
    private ClientGameResult myClientGameResult;

    public StepActiveGame(Deque<Warrior> warriors, Warrior currentWarrior, boolean gameOver) {
        this.warriors = warriors;
        this.currentWarrior = currentWarrior;
        this.gameOver = gameOver;
    }

    public StepActiveGame me(Client me) {
        this.me = me;
        return this;
    }

    public StepActiveGame anotherClient(Client anotherClient) {
        this.anotherClient = anotherClient;
        return this;
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

    public boolean isGameOver() {
        return gameOver;
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
}
