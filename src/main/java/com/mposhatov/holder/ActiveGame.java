package com.mposhatov.holder;

import com.mposhatov.dto.Client;
import com.mposhatov.dto.Warrior;
import com.mposhatov.exception.ActiveGameDoesNotContainedWarriorException;
import com.mposhatov.exception.ClientHasNotActiveGameException;
import com.mposhatov.exception.InvalidCurrentStepInQueueException;

import java.util.*;
import java.util.stream.Collectors;

public class ActiveGame {

    private long id;

    private Date createAt;

    private Client firstClient;

    private Client secondClient;

    private Map<Long, List<Long>> startWarriorIdsByClientIds = new HashMap<>();

    private Map<Long, List<Long>> killedWarriorIdsByClientId = new HashMap<>();

    private Deque<Warrior> queueWarriors = new LinkedList<>();

    private Map<Long, Warrior> warriorByIds = new HashMap<>();

    private Long lastWarriorId;

    private Long winClientId;

    private boolean gameOver = false;

    public ActiveGame(long id, Client firstClient, Client secondClient, List<Warrior> queueWarriors) {

        this.createAt = new Date();

        this.id = id;

        this.firstClient = firstClient;
        this.secondClient = secondClient;

        this.queueWarriors = new LinkedList<>(queueWarriors);

        this.warriorByIds = queueWarriors.stream().collect(Collectors.toMap(Warrior::getId, w -> w));

        startWarriorIdsByClientIds.put(firstClient.getId(),
                firstClient.getHero().getWarriors().stream().map(Warrior::getId).collect(Collectors.toList()));

        startWarriorIdsByClientIds.put(secondClient.getId(),
                secondClient.getHero().getWarriors().stream().map(Warrior::getId).collect(Collectors.toList()));

        lastWarriorId = queueWarriors.stream().mapToLong(Warrior::getId).max().orElse(0);

        killedWarriorIdsByClientId.put(firstClient.getId(), new ArrayList<>());
        killedWarriorIdsByClientId.put(secondClient.getId(), new ArrayList<>());
    }

    public Warrior registerDeadWarrior(Long warriorId) throws ClientHasNotActiveGameException {

        final Warrior warrior = warriorByIds.get(warriorId);

        final Client defendClient = getClientByClientId(warrior.getHero().getClient().getId());

        if (defendClient == null) {
            throw new ClientHasNotActiveGameException(warrior.getHero().getClient().getId());
        }

        final Client attackClient = defendClient.getId() == firstClient.getId() ? secondClient : firstClient;

        removeWarrior(warrior);

        final List<Long> killedWarriorIds = killedWarriorIdsByClientId.get(attackClient.getId());

        killedWarriorIds.add(warriorId);

        return warrior;
    }

    public ActiveGame stepUp() {
        final Warrior warrior = queueWarriors.pollFirst();
        queueWarriors.addLast(warrior);
        return this;
    }

    public Warrior getCurrentWarrior() throws InvalidCurrentStepInQueueException {

        if (queueWarriors.isEmpty()) {
            throw new InvalidCurrentStepInQueueException(id);
        }

        final Warrior warrior = queueWarriors.getFirst();

        if (warrior == null) {
            throw new InvalidCurrentStepInQueueException(id);
        }

        return warrior;
    }

    public ActiveGame setWinClient(long clientId) {
        this.winClientId = clientId;
        return this;
    }

    public Warrior getWarriorById(long warriorId) throws ActiveGameDoesNotContainedWarriorException {
        final Warrior warrior = warriorByIds.get(warriorId);
        if (warrior == null) {
            throw new ActiveGameDoesNotContainedWarriorException(this.id, warriorId);
        }
        return warrior;
    }

    public boolean isFirstRowFree(long clientId) throws ClientHasNotActiveGameException {

        final Client client = getClientByClientId(clientId);

        if (client == null) {
            throw new ClientHasNotActiveGameException(clientId);
        }

        return client.getHero().getWarriors().stream().noneMatch(w -> w.getPosition() >= 1 && w.getPosition() <= 7);
    }

    public boolean isColumnFree(Long clientId, Integer position) throws ClientHasNotActiveGameException {

        final Client client = getClientByClientId(clientId);

        if (client == null) {
            throw new ClientHasNotActiveGameException(clientId);
        }

        return client.getHero().getWarriors().stream().noneMatch(w -> w.getPosition().equals(position));
    }

    public Long getAnotherClient(Long clientId) {
        return clientId == firstClient.getId() ? secondClient.getId() : firstClient.getId();
    }

    public Client getClientByClientId(Long clientId) {

        Client client = null;

        if (firstClient.getId() == clientId) {
            client = firstClient;
        } else if (secondClient.getId() == clientId) {
            client = secondClient;
        }

        return client;
    }

    public ActiveGame gameOver() {
        this.gameOver = true;
        return this;
    }

    public void addWarrior(Warrior warrior) {

        final Long clientId = warrior.getHero().getClient().getId();

        final Client client = getClientByClientId(clientId);

        client.getHero().addWarrior(warrior);
        warriorByIds.put(warrior.getId(), warrior);
        queueWarriors.addLast(warrior);

        this.lastWarriorId = warrior.getId() > this.lastWarriorId ? warrior.getId() : this.lastWarriorId;
    }

    private void removeWarrior(Warrior warrior) {

        final Long clientId = warrior.getHero().getClient().getId();

        final Client client = getClientByClientId(clientId);

        client.getHero().getWarriors().remove(warrior);
        queueWarriors.removeAll(Collections.singleton(warrior));
    }

    public Long generateWarriorId() {
        return this.lastWarriorId + 1;
    }

    public boolean existCurrentWarrior() {
        return !queueWarriors.isEmpty();
    }

    public List<Long> getStartWarriorsByClientId(Long clientId) {
        return startWarriorIdsByClientIds.get(clientId);
    }

    public List<Long> getKilledWarriorIdsByClientId(Long clientId) {
        return killedWarriorIdsByClientId.get(clientId);
    }

    public List<Client> getClients() {
        return Arrays.asList(firstClient, secondClient);
    }

    public long getId() {
        return id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Long getWinClientId() {
        return winClientId;
    }

    public Client getFirstClient() {
        return firstClient;
    }

    public Client getSecondClient() {
        return secondClient;
    }

    public Deque<Warrior> getQueueWarriors() {
        return queueWarriors;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
