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

    private Client firstClient;

    private Client secondClient;

    private Map<Long, Client> clientByIds = new HashMap<>();

    private Map<Long, Long> receivedExperienceByClientIds = new HashMap<>();

    private Deque<Warrior> queueWarriors = new LinkedList<>();

    private Map<Long, Warrior> warriorByIds = new HashMap<>();

    private List<Long> winClients = new ArrayList<>();

    public ActiveGame(long id, Client firstClient, Client secondClient, List<Warrior> queueWarriors) {

        this.createAt = new Date();

        this.id = id;

        this.firstClient = firstClient;
        this.secondClient = secondClient;

        this.clientByIds = Stream.of(firstClient, secondClient).collect(Collectors.toMap(Client::getId, cl -> cl));

        this.queueWarriors = new LinkedList<>(queueWarriors);

        this.warriorByIds = queueWarriors.stream().collect(Collectors.toMap(Warrior::getId, w -> w));

        this.receivedExperienceByClientIds.put(firstClient.getId(), 0L);
        this.receivedExperienceByClientIds.put(secondClient.getId(), 0L);
    }

    public boolean registerDeadWarriors(List<Long> warriors) throws ActiveGameDoesNotContainTwoClientsException {

        if (warriors.isEmpty()) {
            return false;
        }

        boolean win = false;

        for (Long warriorId : warriors) {
            final Warrior warrior = warriorByIds.get(warriorId);

            final Client client = clientByIds.get(warrior.getHero().getClient().getId());

            final Client attackClient = clientByIds.values().stream()
                    .filter(cl -> cl.getId() != client.getId())
                    .findFirst()
                    .orElseThrow(() -> new ActiveGameDoesNotContainTwoClientsException(this.id));

            receivedExperienceByClientIds.put(
                    attackClient.getId(),
                    receivedExperienceByClientIds.get(attackClient.getId()) + (long) (warrior.getKilledExperience()));

            client.getHero().getWarriors().remove(warrior);
            queueWarriors.remove(warrior);
            warriorByIds.remove(warrior.getId());
        }

        for (Client client : clientByIds.values()) {
            if (client.getHero().getWarriors().size() == 0) {
                win = true;
                winClients.add(client.getId());
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
        return new ArrayList<>(clientByIds.values());
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
