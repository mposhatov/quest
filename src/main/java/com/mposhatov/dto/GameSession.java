package com.mposhatov.dto;

import java.util.*;

public class GameSession {

    private long id;

    private Map<Command, Client> clientByCommands = new HashMap<>();

    private List<Warrior> queueWarriors = new ArrayList<>();
    private long currentStep;

    private Map<String, Warrior> warriors = new HashMap<>();

    public GameSession(long id, Map<Command, Client> clientByCommands, List<Warrior> queueWarriors, Map<String, Warrior> warriors) {
        this.id = id;
        this.clientByCommands = clientByCommands;
        this.queueWarriors = queueWarriors;
        this.currentStep = 0;
        this.warriors = warriors;
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

    public Map<String, Warrior> getWarriors() {
        return warriors;
    }

    public long getCurrentStep() {
        return currentStep;
    }
}
