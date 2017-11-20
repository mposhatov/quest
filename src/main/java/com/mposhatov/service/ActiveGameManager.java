package com.mposhatov.service;

import com.mposhatov.dao.*;
import com.mposhatov.dto.StepActiveGame;
import com.mposhatov.entity.*;
import com.mposhatov.holder.ActiveGame;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Warrior;
import com.mposhatov.exception.*;
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

    public DbClosedGame closeGame(long activeGameId) throws ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, InvalidCurrentStepInQueueException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);

        if (activeGame.getFirstClient() == null || activeGame.getSecondClient() == null) {
            throw new ActiveGameDoesNotContainTwoClientsException(activeGame.getId());
        }

        if (activeGame.getWinClientIds() == null || activeGame.getWinClientIds().size() < 1 || activeGame.getWinClientIds().size() > 2) {
            throw new ActiveGameDoesNotContainWinClientException(activeGame.getId());
        }

        final DbClient dbFirstClient = clientRepository.getOne(activeGame.getFirstClient().getId());
        final DbClient dbSecondClient = clientRepository.getOne(activeGame.getSecondClient().getId());

        if(activeGame.getWinClientIds().contains(dbFirstClient.getId())) {
            addExperience(
                    dbFirstClient.getHero(),
                    warriorRepository.findAll(activeGame.getStartWarriorsByClientId(dbFirstClient.getId())),
                    warriorRepository.findAll(activeGame.getStartWarriorsByClientId(dbSecondClient.getId())));
        } else {
            addExperience(
                    dbFirstClient.getHero(),
                    warriorRepository.findAll(activeGame.getStartWarriorsByClientId(dbFirstClient.getId())),
                    warriorRepository.findAll(activeGame.getKilledWarriorIdsByClientId(dbFirstClient.getId())));
        }

        if(activeGame.getWinClientIds().contains(dbSecondClient.getId())) {
            addExperience(
                    dbSecondClient.getHero(),
                    warriorRepository.findAll(activeGame.getStartWarriorsByClientId(dbSecondClient.getId())),
                    warriorRepository.findAll(activeGame.getStartWarriorsByClientId(dbFirstClient.getId())));
        } else {
            addExperience(
                    dbSecondClient.getHero(),
                    warriorRepository.findAll(activeGame.getStartWarriorsByClientId(dbSecondClient.getId())),
                    warriorRepository.findAll(activeGame.getKilledWarriorIdsByClientId(dbSecondClient.getId())));
        }

        DbClosedGame closedGame = closedGameRepository.saveAndFlush(new DbClosedGame(activeGame.getCreateAt()));

        final boolean firstClientWin = activeGame.getWinClientIds().contains(dbFirstClient.getId());
        final boolean secondClientWin = activeGame.getWinClientIds().contains(dbSecondClient.getId());

        DbClientGameResult firstClientGameResult =
                new DbClientGameResult(dbFirstClient, closedGame, firstClientWin, firstClientWin ? 5 : -5);

        DbClientGameResult secondClientGameResult =
                new DbClientGameResult(dbSecondClient, closedGame, secondClientWin, secondClientWin ? 5 : -5);

        dbFirstClient.addRating(firstClientGameResult.getRating());
        dbSecondClient.addRating(secondClientGameResult.getRating());

        closedGame.addGameResults(Arrays.asList(firstClientGameResult, secondClientGameResult));

        activeGameHolder.deregisterActiveGame(activeGame.getId());

        return closedGame;
    }

    private DbHero addExperience(DbHero hero, List<DbWarrior> destinationWarriors, List<DbWarrior> sourceWarriors) {

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
            warrior = warrior.addExperience(experienceByOne);

            DbHierarchyWarrior currentHierarchyWarrior = warrior.getHierarchyWarrior();

            while (warrior.getExperience() >= currentHierarchyWarrior.getImprovementExperience()) {

                final DbHierarchyWarrior nextHierarchyWarrior =
                        hierarchyWarriorRepository.findNextAvailableToUpdateForHero(hero, currentHierarchyWarrior);

                if (nextHierarchyWarrior != null) {
                    warrior.hierarchyWarrior(nextHierarchyWarrior);
                    warrior.addExperience(warrior.getExperience() - currentHierarchyWarrior.getImprovementExperience());
                    currentHierarchyWarrior = nextHierarchyWarrior;
                } else {
                    break;
                }

            }

        }

        return hero;
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

    public StepActiveGame registerStepClosingActiveGame(ActiveGame activeGame) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException {
        return registerStepActiveGame(activeGame, false, null, null, true, null);
    }

    private StepActiveGame registerStepActiveGame(ActiveGame activeGame, boolean stepUp, Long attackWarriorId, Long defendingWarriorId, boolean close, Long currentClientId) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, ActiveGameDoesNotContainTwoClientsException {

        if (stepUp) {
            activeGame.stepUp();
        }

        final StepActiveGame stepActiveGameFirstClient =
                EntityConverter.toStepActiveGame(activeGame, activeGame.getFirstClient().getId());

        final StepActiveGame stepActiveGameSecondClient =
                EntityConverter.toStepActiveGame(activeGame, activeGame.getSecondClient().getId());

        if (attackWarriorId != null) {
            stepActiveGameFirstClient.setAttackWarriorId(attackWarriorId);
            stepActiveGameSecondClient.setAttackWarriorId(attackWarriorId);
        }

        if (defendingWarriorId != null) {
            stepActiveGameFirstClient.setDefendWarriorId(defendingWarriorId);
            stepActiveGameSecondClient.setDefendWarriorId(defendingWarriorId);
        }

        if (close) {
            final DbClosedGame dbClosedGame = closeGame(activeGame.getId());
            stepActiveGameFirstClient.setClosedGameId(dbClosedGame.getId());
            stepActiveGameSecondClient.setClosedGameId(dbClosedGame.getId());
        }

        if (currentClientId != null) {
            if (currentClientId == activeGame.getFirstClient().getId()) {
                getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame.getSecondClient().getId(), stepActiveGameSecondClient);
                return stepActiveGameFirstClient;
            } else if (currentClientId == activeGame.getSecondClient().getId()) {
                getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame.getFirstClient().getId(), stepActiveGameFirstClient);
                return stepActiveGameSecondClient;
            }
        }

        getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame.getFirstClient().getId(), stepActiveGameFirstClient);
        getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame.getSecondClient().getId(), stepActiveGameSecondClient);

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