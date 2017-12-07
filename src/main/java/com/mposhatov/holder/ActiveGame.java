package com.mposhatov.holder;

import com.mposhatov.dto.*;
import com.mposhatov.exception.ActiveGameException;
import com.mposhatov.exception.ClientException;

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

    private Long lastEffectId;

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

        lastEffectId = 0L;

        killedWarriorIdsByClientId.put(firstClient.getId(), new ArrayList<>());
        killedWarriorIdsByClientId.put(secondClient.getId(), new ArrayList<>());
    }

    public Warrior registerDeadWarrior(Long warriorId) throws ClientException.HasNotActiveGame {

        final Warrior warrior = warriorByIds.get(warriorId);

        final Client defendClient = getClientByClientId(warrior.getHero().getClient().getId());

        if (defendClient == null) {
            throw new ClientException.HasNotActiveGame(warrior.getHero().getClient().getId());
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

    public boolean isFirstRowFree(long clientId) throws ClientException.HasNotActiveGame {

        final Client client = getClientByClientId(clientId);

        if (client == null) {
            throw new ClientException.HasNotActiveGame(clientId);
        }

        return client.getHero().getWarriors().stream().noneMatch(w -> w.getPosition() >= 1 && w.getPosition() <= 7);
    }

    public boolean isColumnFree(Long clientId, Integer position) throws ClientException.HasNotActiveGame {

        final Client client = getClientByClientId(clientId);

        if (client == null) {
            throw new ClientException.HasNotActiveGame(clientId);
        }

        return client.getHero().getWarriors().stream().noneMatch(w -> w.getPosition().equals(position));
    }

    public ActiveGame gameOver() {
        this.gameOver = true;
        return this;
    }

    public Warrior getCurrentWarrior() throws ActiveGameException.InvalidCurrentStepInQueue {

        if (queueWarriors.isEmpty()) {
            throw new ActiveGameException.InvalidCurrentStepInQueue(this.id);
        }

        final Warrior warrior = queueWarriors.getFirst();

        if (warrior == null) {
            throw new ActiveGameException.InvalidCurrentStepInQueue(this.id);
        }

        return warrior;
    }

    public ActiveGame setWinClient(long clientId) {
        this.winClientId = clientId;
        return this;
    }

    public Warrior getWarriorById(long warriorId) throws ActiveGameException.DoesNotContainedWarrior {

        final Warrior warrior = warriorByIds.get(warriorId);

        if (warrior == null) {
            throw new ActiveGameException.DoesNotContainedWarrior(this.id, warriorId);
        }
        return warrior;
    }

    public Warrior addWarrior(HierarchyWarrior hierarchyWarrior, Integer position, Hero hero) {

        final Warrior warrior = new Warrior(
                ++this.lastWarriorId,
                hierarchyWarrior.getName(),
                hierarchyWarrior.getPictureName(),
                hierarchyWarrior.getLevel(),
                hierarchyWarrior.getKilledExperience(),
                hierarchyWarrior.getImprovementExperience(),
                true,
                position,
                hero,
                hierarchyWarrior.getWarriorCharacteristics(),
                0,
                hierarchyWarrior.getSpellAttacks(),
                hierarchyWarrior.getSpellHeals(),
                hierarchyWarrior.getSpellExhortations(),
                hierarchyWarrior.getSpellPassives());

        final Long clientId = warrior.getHero().getClient().getId();

        final Client client = getClientByClientId(clientId);

        client.getHero().addWarrior(warrior);
        warriorByIds.put(warrior.getId(), warrior);
        queueWarriors.addLast(warrior);

        this.lastWarriorId = warrior.getId() > this.lastWarriorId ? warrior.getId() : this.lastWarriorId;

        return warrior;
    }

    private void removeWarrior(Warrior warrior) {

        final Long clientId = warrior.getHero().getClient().getId();

        final Client client = getClientByClientId(clientId);

        client.getHero().getWarriors().remove(warrior);
        queueWarriors.removeAll(Collections.singleton(warrior));
    }

    public Effect addEffect(Warrior castingWarrior, Warrior targetWarrior, SpellPassive spellPassive) {

        final Effect effect = new Effect(
                ++this.lastEffectId,
                spellPassive.getName(),
                spellPassive.getDescription(),
                spellPassive.getPictureName(),
                spellPassive.getCharacteristics(),
                castingWarrior.getWarriorCharacteristics().getSpellPower());

        if (spellPassive.isSummed()) {

            final List<Effect> summedEffects =
                    targetWarrior.getEffects().stream().filter(ef -> ef.getName().equals(effect.getName())).collect(Collectors.toList());

            if (summedEffects == null || summedEffects.size() < spellPassive.getMaxSummed()) {
                targetWarrior.addEffect(effect);
            } else {
                summedEffects.stream()
                        .min(Comparator.comparingInt(Effect::getLeftSteps))
                        .ifPresent(Effect::refresh);
            }
        } else if (targetWarrior.getEffects().stream().map(Effect::getName).collect(Collectors.toList()).contains(effect.getName())) {
            targetWarrior.refreshEffect(effect.getName());
        } else {
            targetWarrior.addEffect(effect);
        }

        return effect;
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

    public List<Long> getStartWarriorsByClientId(Long clientId) {
        return startWarriorIdsByClientIds.get(clientId);
    }

    public List<Long> getKilledWarriorIdsByClientId(Long clientId) {
        return killedWarriorIdsByClientId.get(clientId);
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
