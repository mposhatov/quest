package com.mposhatov.dto;

import com.mposhatov.entity.Command;
import com.mposhatov.exception.ActiveGameDoesNotContainCommandsException;
import com.mposhatov.exception.ActiveGameDoesNotContainedWarriorException;
import com.mposhatov.exception.InvalidCurrentStepInQueueException;

import java.util.*;

public class ActiveGame {

    private long id;

    private Date createAt;

    private Map<Command, Client> clientByCommands = new HashMap<>();

    private List<Warrior> queueWarriors = new ArrayList<>();
    private int currentStep;

    private Map<Long, Warrior> warriorByIds = new HashMap<>();

    private Command winCommand;

    private boolean updateForFirstClient = false;
    private boolean updateForSecondClient = false;

    public ActiveGame(long id, Map<Command, Client> clientByCommands, List<Warrior> queueWarriors, Map<Long, Warrior> warriorByIds) {
        this.id = id;
        this.clientByCommands = clientByCommands;
        this.queueWarriors = queueWarriors;
        this.warriorByIds = warriorByIds;
        this.currentStep = 0;
        this.createAt = new Date();
    }

    public boolean registerDeadWarrior(Warrior warrior) throws ActiveGameDoesNotContainCommandsException {

        boolean win = false;

        final Client defendClient = getClientByCommand(warrior.getCommand());

        defendClient.getHero().getWarriors().remove(warrior);

        queueWarriors.remove(warrior);

        warriorByIds.remove(warrior.getId());

        Command attackCommand = null;

        if (warrior.getCommand().equals(Command.COMMAND_1)) {
            attackCommand = Command.COMMAND_2;
        } else if (warrior.getCommand().equals(Command.COMMAND_2)) {
            attackCommand = Command.COMMAND_1;
        }

        if (attackCommand != null && defendClient.getHero().getWarriors().size() == 0) {
            this.winCommand = attackCommand;
            win = true;
        }

        return win;
    }

    public boolean isWin(Command command) throws ActiveGameDoesNotContainCommandsException {
        return winCommand.equals(command);
    }

    public ActiveGame stepUp() {
        this.currentStep++;
        if (this.currentStep >= this.queueWarriors.size()) {
            this.currentStep = 0;
        }
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
        final Warrior warrior = warriorByIds.get(warriorId);
        if (warrior == null) {
            throw new ActiveGameDoesNotContainedWarriorException(this.id, warriorId);
        }
        return warrior;
    }

    public long getId() {
        return id;
    }

    public Client getClientByCommand(Command command) throws ActiveGameDoesNotContainCommandsException {
        final Client client = clientByCommands.get(command);

        if (client == null) {
            throw new ActiveGameDoesNotContainCommandsException(this.id);
        }

        return client;
    }

    public Command getCommandByClientId(long clientId) throws ActiveGameDoesNotContainCommandsException {
        return clientByCommands.entrySet().stream()
                .filter(es -> es.getValue().getId() == clientId)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new ActiveGameDoesNotContainCommandsException(this.id));
    }

    public List<Client> getClients() {
        return new ArrayList<>(clientByCommands.values());
    }

    public ActiveGame update() {
        this.updateForFirstClient = true;
        this.updateForSecondClient = true;
        return this;
    }

    public boolean isUpdated(Command command) {
        boolean update = false;
        if (command.equals(Command.COMMAND_1)) {
            update = this.updateForFirstClient;
        }
        if (command.equals(Command.COMMAND_2)) {
            update = this.updateForSecondClient;
        }
        return update;
    }

    public void acceptUpdate(Command command) {
        if (command.equals(Command.COMMAND_1)) {
            this.updateForFirstClient = false;
        }
        if (command.equals(Command.COMMAND_2)) {
            this.updateForSecondClient = false;
        }
    }

    public Map<Command, Client> getClientByCommands() {
        return clientByCommands;
    }

    public List<Warrior> getQueueWarriors() {
        return queueWarriors;
    }

    public Map<Long, Warrior> getWarriorByIds() {
        return warriorByIds;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public Command getWinCommand() {
        return winCommand;
    }

    public boolean isUpdateForFirstClient() {
        return updateForFirstClient;
    }

    public Date getCreateAt() {
        return createAt;
    }
}
