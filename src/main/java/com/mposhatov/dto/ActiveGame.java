package com.mposhatov.dto;

import com.mposhatov.exception.ActiveGameDoesNotContainedWarriorException;
import com.mposhatov.exception.InvalidCurrentStepInQueueException;

import java.util.*;

public class ActiveGame {

    private long id;

    private Map<Command, Client> clientByCommands = new HashMap<>();

    private List<Warrior> queueWarriors = new ArrayList<>();
    private int currentStep;

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

    public Warrior getCurrentWarrior() throws InvalidCurrentStepInQueueException {
        final Warrior warrior = queueWarriors.get(currentStep);
        if (warrior == null) {
            throw new InvalidCurrentStepInQueueException(id, currentStep);
        }
        return warrior;
    }

    public Warrior getWarriorById(long warriorId) throws ActiveGameDoesNotContainedWarriorException {
        final Warrior warrior = warriors.get(warriorId);
        if (warrior == null) {
            throw new ActiveGameDoesNotContainedWarriorException(this.id, warriorId);
        }
        return warrior;
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

    public int getCurrentStep() {
        return currentStep;
    }
}
