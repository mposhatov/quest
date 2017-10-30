package com.mposhatov.holder;

import com.mposhatov.dto.Client;
import com.mposhatov.dto.Warrior;
import com.mposhatov.exception.ActiveGameDoesNotContainTwoClientsException;
import com.mposhatov.exception.ActiveGameDoesNotContainedWarriorException;
import com.mposhatov.exception.InvalidCurrentStepInQueueException;

import java.util.*;
import java.util.stream.Collectors;

public class ActiveGame {

    private long id;

    private Date createAt;

    private Client firstClient;

    private Client secondClient;

    private Map<Long, Long> receivedExperienceByClientIds = new HashMap<>();

    private Deque<Warrior> queueWarriors = new LinkedList<>();

    private Map<Long, Warrior> warriorByIds = new HashMap<>();

    private List<Long> winClients = new ArrayList<>();

    public ActiveGame(long id, Client firstClient, Client secondClient, List<Warrior> queueWarriors) {

        this.createAt = new Date();

        this.id = id;

        this.firstClient = firstClient;
        this.secondClient = secondClient;

        this.queueWarriors = new LinkedList<>(queueWarriors);

        this.warriorByIds = queueWarriors.stream().collect(Collectors.toMap(Warrior::getId, w -> w));

        this.receivedExperienceByClientIds.put(firstClient.getId(), 0L);
        this.receivedExperienceByClientIds.put(secondClient.getId(), 0L);
    }

    public boolean registerDeadWarriors(List<Long> warriors) throws ActiveGameDoesNotContainTwoClientsException {

        boolean win = false;

        if (!warriors.isEmpty()) {
            for (Long warriorId : warriors) {
                final Warrior warrior = warriorByIds.get(warriorId);

                final Client defendClient =
                        warrior.getHero().getClient().getId() == firstClient.getId() ? firstClient : secondClient;

                final Client attackClient = defendClient.getId() == firstClient.getId() ? secondClient : firstClient;

                receivedExperienceByClientIds.put(
                        attackClient.getId(),
                        receivedExperienceByClientIds.get(attackClient.getId()) + (long) (warrior.getKilledExperience()));

                defendClient.getHero().getWarriors().remove(warrior);
                queueWarriors.removeAll(Collections.singleton(warrior));
                warriorByIds.remove(warrior.getId());

                if (defendClient.getHero().getWarriors().isEmpty()) {
                    winClients.add(attackClient.getId());
                    win = true;
                }
            }
        }

        return win;
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

    public ActiveGame setWinClient(Long clientId) {
        this.winClients.add(clientId);
        return this;
    }

    public Warrior getWarriorById(long warriorId) throws ActiveGameDoesNotContainedWarriorException {
        final Warrior warrior = warriorByIds.get(warriorId);
        if (warrior == null) {
            throw new ActiveGameDoesNotContainedWarriorException(this.id, warriorId);
        }
        return warrior;
    }

    public boolean existCurrentWarrior() {
        return !queueWarriors.isEmpty();
    }

    public Long getReceivedExperienceByClientId(Long clientId) {
        return receivedExperienceByClientIds.get(clientId);
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

    public List<Long> getWinClients() {
        return winClients;
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
}
