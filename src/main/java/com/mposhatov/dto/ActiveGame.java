package com.mposhatov.dto;

import java.util.*;

public class ActiveGame {

    private long id;

    private Map<Command, Client> clientByCommands = new HashMap<>();

    private List<Warrior> queueWarriors = new ArrayList<>();
    private long currentStep;

    private Map<Long, Warrior> warriors = new HashMap<>();

    public ActiveGame(long id, Map<Command, Client> clientByCommands, List<Warrior> queueWarriors, Map<Long, Warrior> warriors) {
        this.id = id;
        this.clientByCommands = clientByCommands;
        this.queueWarriors = queueWarriors;
        this.currentStep = 0;
        this.warriors = warriors;
    }

    public ActiveGame stepUp() {
        this.currentStep++;
        return this;
    }

    public long getId() {
        return id;
    }

    public Map<Command, Client> getClientByCommands() {
        return clientByCommands;
    }

    public List<Warrior> getQueueWarriors() {
        return queueWarriors;
    }

    public Map<Long, Warrior> getWarriors() {
        return warriors;
    }

    public long getCurrentStep() {
        return currentStep;
    }
}
