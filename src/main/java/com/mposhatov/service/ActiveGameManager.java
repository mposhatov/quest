package com.mposhatov.service;

import com.mposhatov.dao.*;
import com.mposhatov.dto.*;
import com.mposhatov.entity.*;
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGame;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.request.GetUpdatedActiveGameProcessor;
import com.mposhatov.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ActiveGameManager {

    private final Logger logger = LoggerFactory.getLogger(ActiveGameManager.class);

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Autowired
    private GetUpdatedActiveGameProcessor getUpdatedActiveGameProcessor;

    @Autowired
    private ClosedGameRepository closedGameRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private HeroLevelRequirementRepository heroLevelRequirementRepository;

    @Autowired
    private WarriorRepository warriorRepository;

    @Autowired
    private HierarchyWarriorRepository hierarchyWarriorRepository;

    public ActiveGame createGame(Client firstClient, Client secondClient) throws ClientIsNotInTheQueueException, InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, ActiveGameDoesNotContainTwoClientsException {

        final List<Warrior> queueWarriors = new LinkedList<>();

        queueWarriors.addAll(firstClient.getHero().getWarriors());
        queueWarriors.addAll(secondClient.getHero().getWarriors());

        queueWarriors.sort(Comparator.comparing(o -> o.getWarriorCharacteristics().getVelocity()));

        final ActiveGame activeGame = new ActiveGame(
                activeGameHolder.generateActiveGameId(), firstClient, secondClient, queueWarriors);

        activeGameHolder.registerActiveGame(activeGame);

        registerFirstStepActiveGame(activeGame);

        return activeGame;
    }

    public ClosedGame closeGame(long activeGameId) throws ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, InvalidCurrentStepInQueueException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);

        if (activeGame.getFirstClient() == null || activeGame.getSecondClient() == null) {
            throw new ActiveGameDoesNotContainTwoClientsException(activeGame.getId());
        }

        if (activeGame.getWinClientIds() == null || activeGame.getWinClientIds().size() < 1 || activeGame.getWinClientIds().size() > 2) {
            throw new ActiveGameDoesNotContainWinClientException(activeGame.getId());
        }

        final DbClient dbFirstClient = clientRepository.getOne(activeGame.getFirstClient().getId());
        final DbClient dbSecondClient = clientRepository.getOne(activeGame.getSecondClient().getId());

        final ClientGameResult firstClientGameResult = new ClientGameResult().clientId(dbFirstClient.getId());
        final ClientGameResult secondClientGameResult = new ClientGameResult().clientId(dbSecondClient.getId());

        List<WarriorUpgrade> warriorUpgrades;

        int ratingFirstClient = -5;
        int ratingSecondClient = -5;

        if (activeGame.getWinClientIds().contains(dbFirstClient.getId())) {
            firstClientGameResult.win();
            ratingFirstClient = 5;
            warriorUpgrades = addExperience(
                    dbFirstClient.getHero(),
                    warriorRepository.findAll(activeGame.getStartWarriorsByClientId(dbFirstClient.getId())),
                    warriorRepository.findAll(activeGame.getStartWarriorsByClientId(dbSecondClient.getId())));
        } else {
            warriorUpgrades = addExperience(
                    dbFirstClient.getHero(),
                    warriorRepository.findAll(activeGame.getStartWarriorsByClientId(dbFirstClient.getId())),
                    warriorRepository.findAll(activeGame.getKilledWarriorIdsByClientId(dbFirstClient.getId())));
        }

        firstClientGameResult.warriorUpgrades(warriorUpgrades);

        if (activeGame.getWinClientIds().contains(dbSecondClient.getId())) {
            secondClientGameResult.win();
            ratingSecondClient = 5;
            warriorUpgrades = addExperience(
                    dbSecondClient.getHero(),
                    warriorRepository.findAll(activeGame.getStartWarriorsByClientId(dbSecondClient.getId())),
                    warriorRepository.findAll(activeGame.getStartWarriorsByClientId(dbFirstClient.getId())));
        } else {
            warriorUpgrades = addExperience(
                    dbSecondClient.getHero(),
                    warriorRepository.findAll(activeGame.getStartWarriorsByClientId(dbSecondClient.getId())),
                    warriorRepository.findAll(activeGame.getKilledWarriorIdsByClientId(dbSecondClient.getId())));
        }

        secondClientGameResult.warriorUpgrades(warriorUpgrades);

        final DbClosedGame closedGame = closedGameRepository.saveAndFlush(new DbClosedGame(activeGame.getCreateAt()));

        final boolean firstClientWin = activeGame.getWinClientIds().contains(dbFirstClient.getId());
        final boolean secondClientWin = activeGame.getWinClientIds().contains(dbSecondClient.getId());

        final DbClientGameResult dbFirstClientGameResult =
                new DbClientGameResult(dbFirstClient, closedGame, firstClientWin, ratingFirstClient);

        final DbClientGameResult dbSecondClientGameResult =
                new DbClientGameResult(dbSecondClient, closedGame, secondClientWin, ratingSecondClient);

        dbFirstClient.addRating(firstClientGameResult.getRating());
        dbSecondClient.addRating(secondClientGameResult.getRating());

        firstClientGameResult.rating(ratingFirstClient);
        secondClientGameResult.rating(ratingSecondClient);

        closedGame.addGameResults(Arrays.asList(dbFirstClientGameResult, dbSecondClientGameResult));

        activeGameHolder.deregisterActiveGame(activeGame.getId());

        return new ClosedGame(closedGame.getStartTime(), closedGame.getFinishTime(), firstClientGameResult, secondClientGameResult);
    }

    private List<WarriorUpgrade> addExperience(DbHero hero, List<DbWarrior> destinationWarriors, List<DbWarrior> sourceWarriors) {

        final List<WarriorUpgrade> warriorUpgrades = new ArrayList<>();

        long experience = 0;

        for (DbWarrior warrior : sourceWarriors) {
            experience += warrior.getHierarchyWarrior().getKilledExperience();
        }

        final long experienceByOne = experience / (destinationWarriors.size() + 1);

        hero = hero.addExperience(experienceByOne);

        DbHeroLevelRequirement heroLevelRequirement = heroLevelRequirementRepository.findOne(hero.getLevel() + 1);

        while (hero.getExperience() >= heroLevelRequirement.getRequirementExperience()) {
            hero = hero.upLevel(heroLevelRequirement.getAdditionalHeroPoint());
            heroLevelRequirement = heroLevelRequirementRepository.findOne(hero.getLevel() + 1);
        }

        for (DbWarrior warrior : destinationWarriors) {

            final WarriorUpgrade warriorUpgrade =
                    new WarriorUpgrade().warriorBeforeUpgrade(EntityConverter.toWarrior(warrior, true));

            warrior = warrior.addExperience(experienceByOne);

            DbHierarchyWarrior currentHierarchyWarrior = warrior.getHierarchyWarrior();

            while (warrior.getExperience() >= currentHierarchyWarrior.getImprovementExperience()) {

                final DbHierarchyWarrior nextHierarchyWarrior =
                        hierarchyWarriorRepository.findNextAvailableToUpdateForHero(hero, currentHierarchyWarrior);

                if (nextHierarchyWarrior != null) {
                    warrior.hierarchyWarrior(nextHierarchyWarrior);
                    currentHierarchyWarrior = nextHierarchyWarrior;
                } else {
                    break;
                }

            }

            warriorUpgrade.warriorAfterUpgrade(EntityConverter.toWarrior(warrior, true));
            warriorUpgrades.add(warriorUpgrade);

        }

        return warriorUpgrades;
    }

    public StepActiveGame registerFirstStepActiveGame(ActiveGame activeGame) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException {
        return registerStepActiveGame(activeGame, false, null, null, false, null);
    }

    public StepActiveGame registerFirstStepActiveGame(ActiveGame activeGame, long currentClientId) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException {
        return registerStepActiveGame(activeGame, false, null, null, false, currentClientId);
    }

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException {
        return registerStepActiveGame(activeGame, true, null, null, false, null);
    }

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame, long currentClientId) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException {
        return registerStepActiveGame(activeGame, true, null, null, false, currentClientId);
    }

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame, Long attackWarriorId, Long defendingWarriorId, long currentClientId) throws ActiveGameDoesNotContainTwoClientsException, InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException {


        for (Warrior warrior : activeGame.getQueueWarriors()) {
            if (warrior.getWarriorCharacteristics().getHealth() == 0) {
                activeGame.registerDeadWarrior(warrior.getId());
            }
        }

        StepActiveGame stepActiveGameFirstClient;

        if (activeGame.getWinClientIds() != null && !activeGame.getWinClientIds().isEmpty()) {
            stepActiveGameFirstClient = registerStepActiveGame(activeGame, false, null, null, true, currentClientId);
        } else {
            stepActiveGameFirstClient = registerStepActiveGame(activeGame, true, attackWarriorId, defendingWarriorId, false, currentClientId);
        }

        return stepActiveGameFirstClient;
    }

    public StepActiveGame registerStepClosingActiveGame(ActiveGame activeGame, Long currentClientId) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException {
        return registerStepActiveGame(activeGame, false, null, null, true, currentClientId);
    }

    private StepActiveGame registerStepActiveGame(ActiveGame activeGame, boolean stepUp, Long attackWarriorId, Long defendingWarriorId, boolean close, Long currentClientId) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, ActiveGameDoesNotContainTwoClientsException {

        if (stepUp) {
            activeGame.stepUp();
        }

        final long firstClientId = activeGame.getFirstClient().getId();
        final long secondClientId = activeGame.getSecondClient().getId();

        final StepActiveGame stepActiveGameFirstClient =
                EntityConverter.toStepActiveGame(activeGame, firstClientId);

        final StepActiveGame stepActiveGameSecondClient =
                EntityConverter.toStepActiveGame(activeGame, secondClientId);

        if (attackWarriorId != null) {
            stepActiveGameFirstClient.setAttackWarriorId(attackWarriorId);
            stepActiveGameSecondClient.setAttackWarriorId(attackWarriorId);
        }

        if (defendingWarriorId != null) {
            stepActiveGameFirstClient.setDefendWarriorId(defendingWarriorId);
            stepActiveGameSecondClient.setDefendWarriorId(defendingWarriorId);
        }

        if (close) {
            final ClosedGame closedGame = closeGame(activeGame.getId());

            stepActiveGameFirstClient.myClientGameResult(
                    closedGame.getFirstClientGameResult().getClientId() == stepActiveGameFirstClient.getMe().getId() ?
                            closedGame.getFirstClientGameResult() :
                            closedGame.getSecondClientGameResult());

            stepActiveGameSecondClient.myClientGameResult(
                    closedGame.getFirstClientGameResult().getClientId() == stepActiveGameSecondClient.getMe().getId() ?
                            closedGame.getFirstClientGameResult() :
                            closedGame.getSecondClientGameResult());
        }

        if (currentClientId != null) {
            if (currentClientId == firstClientId) {
                getUpdatedActiveGameProcessor.registerStepActiveGame(secondClientId, stepActiveGameSecondClient);
                return stepActiveGameFirstClient;
            } else if (currentClientId == secondClientId) {
                getUpdatedActiveGameProcessor.registerStepActiveGame(firstClientId, stepActiveGameFirstClient);
                return stepActiveGameSecondClient;
            }
        }

        getUpdatedActiveGameProcessor.registerStepActiveGame(firstClientId, stepActiveGameFirstClient);
        getUpdatedActiveGameProcessor.registerStepActiveGame(secondClientId, stepActiveGameSecondClient);

        return stepActiveGameFirstClient;
    }

    public boolean isPossibleStrike(Warrior attackWarrior, Warrior defendWarrior, ActiveGame activeGame) {
        boolean access = false;

        if ((attackWarrior.getWarriorCharacteristics().getRangeType().equals(RangeType.RANGE)) ||
                (warriorIsFirstRow(attackWarrior) && warriorIsFirstRow(defendWarrior))
                || (warriorIsFirstRow(attackWarrior) && !warriorIsFirstRow(defendWarrior)
                && activeGame.isFirstRowFree(defendWarrior.getHero().getClient().getId()))
                || (!warriorIsFirstRow(attackWarrior) && warriorIsFirstRow(defendWarrior)
                && activeGame.isFirstRowFree(attackWarrior.getHero().getClient().getId()))
                || (!warriorIsFirstRow(attackWarrior) && !warriorIsFirstRow(defendWarrior)
                && activeGame.isFirstRowFree(attackWarrior.getHero().getClient().getId())
                && activeGame.isFirstRowFree(defendWarrior.getHero().getClient().getId()))) {
            access = true;

        }

        return access;
    }

    private boolean warriorIsFirstRow(Warrior warrior) {
        return warrior.getPosition() >= 1 && warrior.getPosition() <= 7;
    }

}