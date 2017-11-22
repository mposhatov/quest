package com.mposhatov.holder;

import com.mposhatov.dto.Client;
import com.mposhatov.dto.Warrior;
import com.mposhatov.exception.ActiveGameDoesNotContainedWarriorException;
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
    }

    public Warrior registerDeadWarrior(Long warriorId) {

        final Warrior warrior = warriorByIds.get(warriorId);

        final Client defendClient =
                warrior.getHero().getClient().getId() == firstClient.getId() ? firstClient : secondClient;

        final Client attackClient = defendClient.getId() == firstClient.getId() ? secondClient : firstClient;

        defendClient.getHero().getWarriors().remove(warrior);
        queueWarriors.removeAll(Collections.singleton(warrior));
        warriorByIds.remove(warrior.getId());

        final List<Long> killedWarriorIds =
                killedWarriorIdsByClientId.computeIfAbsent(attackClient.getId(), k -> new ArrayList<>());
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

    public boolean isFirstRowFree(long clientId) {
        Client client = firstClient.getId() == clientId ? firstClient : secondClient;
        return client.getHero().getWarriors().stream().noneMatch(w -> w.getPosition() >= 1 && w.getPosition() <= 7);
    }

    public Long getAnotherClient(Long clientId) {
        return clientId == firstClient.getId() ? secondClient.getId() : firstClient.getId();
    }

    public ActiveGame gameOver() {
        this.gameOver = true;
        return this;
    }

    public boolean existCurrentWarrior() {
        return !queueWarriors.isEmpty();
    }

    public List<Long> getStartWarriorsByClientId(long clientId) {
        return startWarriorIdsByClientIds.get(clientId);
    }

    public List<Long> getKilledWarriorIdsByClientId(long clientId) {
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
