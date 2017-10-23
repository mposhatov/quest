package com.mposhatov.holder;

import com.mposhatov.dto.Client;
import com.mposhatov.dto.Warrior;
import com.mposhatov.exception.ActiveGameDoesNotContainTwoClientsException;
import com.mposhatov.exception.ActiveGameDoesNotContainedWarriorException;
import com.mposhatov.exception.InvalidCurrentStepInQueueException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActiveGame {

    private long id;

    private Date createAt;

    private Map<Long, Client> clientByIds = new HashMap<>();

    private List<Warrior> queueWarriors = new ArrayList<>();
    private int currentStep = 0;

    private Map<Long, Warrior> warriorByIds = new HashMap<>();

    private Client winClient;

    public ActiveGame(long id, Client firstClient, Client secondClient, List<Warrior> queueWarriors) {

        this.id = id;

        this.clientByIds = Stream.of(firstClient, secondClient).collect(Collectors.toMap(Client::getId, cl -> cl));

        this.queueWarriors = queueWarriors;
        this.warriorByIds = queueWarriors.stream().collect(Collectors.toMap(Warrior::getId, w -> w));

        this.createAt = new Date();
    }

    public boolean registerDeadWarrior(Warrior warrior) throws ActiveGameDoesNotContainTwoClientsException {

        boolean win = false;

        final Client defendClient = clientByIds.get(warrior.getHero().getClient().getId());

        defendClient.getHero().getWarriors().remove(warrior);
        queueWarriors.remove(warrior);
        warriorByIds.remove(warrior.getId());

        if (defendClient.getHero().getWarriors().size() == 0) {
            win = true;
            winClient = clientByIds.values().stream().filter(cl -> !cl.equals(defendClient)).findFirst().orElse(null);
        }

        return win;
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

    public Client getClientById(long clientId) throws ActiveGameDoesNotContainTwoClientsException {
        final Client client = clientByIds.get(clientId);

        if (client == null) {
            throw new ActiveGameDoesNotContainTwoClientsException(this.id);
        }

        return client;
    }

    public List<Client> getClients() {
        return new ArrayList<>(clientByIds.values());
    }

    public long getId() {
        return id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Client getWinClient() {
        return winClient;
    }

    public List<Warrior> getQueueWarriors() {
        return queueWarriors;
    }


}