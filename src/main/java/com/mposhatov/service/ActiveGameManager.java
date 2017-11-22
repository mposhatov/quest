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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${game.ratingByWin}")
    private int ratingByWin;

    public ActiveGame createGame(Client firstClient, Client secondClient) throws ClientIsNotInTheQueueException, InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, ActiveGameDoesNotContainTwoClientsException, CloseActiveGameException {

        final List<Warrior> queueWarriors = new LinkedList<>();

        queueWarriors.addAll(firstClient.getHero().getWarriors());
        queueWarriors.addAll(secondClient.getHero().getWarriors());

        queueWarriors.sort(Comparator.comparing(o -> o.getWarriorCharacteristics().getVelocity()));

        final ActiveGame activeGame = new ActiveGame(
                activeGameHolder.generateActiveGameId(), firstClient, secondClient, queueWarriors);

        activeGameHolder.registerActiveGame(activeGame);

        registerStepActiveGame(activeGame);

        return activeGame;
    }

    public boolean refresh(ActiveGame activeGame) throws ActiveGameDoesNotExistException, GetUpdateActiveGameRequestDoesNotExistException, CloseActiveGameException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainWinClientException, ActiveGameDoesNotContainTwoClientsException {

        for (Warrior warrior : activeGame.getQueueWarriors()) {
            if (warrior.getWarriorCharacteristics().getHealth() == 0) {
                activeGame.registerDeadWarrior(warrior.getId());
            }
        }

        if (activeGame.getFirstClient().getHero().getWarriors().isEmpty()) {
            activeGame.setWinClient(activeGame.getFirstClient().getId());
            activeGame.gameOver();
        } else if (activeGame.getSecondClient().getHero().getWarriors().isEmpty()) {
            activeGame.setWinClient(activeGame.getSecondClient().getId());
            activeGame.gameOver();
        }

        if (!activeGame.isGameOver()) {
            activeGame.stepUp();
        }

        return activeGame.isGameOver();
    }

    public ClosedGame closeGame(long activeGameId) throws ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, InvalidCurrentStepInQueueException, CloseActiveGameException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);

        if (activeGame.getFirstClient() == null || activeGame.getSecondClient() == null) {
            throw new ActiveGameDoesNotContainTwoClientsException(activeGame.getId());
        }

        if (!activeGame.isGameOver()) {
            throw new CloseActiveGameException(activeGameId);
        }

        final DbClient dbFirstClient = clientRepository.getOne(activeGame.getFirstClient().getId());
        final DbClient dbSecondClient = clientRepository.getOne(activeGame.getSecondClient().getId());

        ClientGameResult firstClientGameResult = new ClientGameResult();
        ClientGameResult secondClientGameResult = new ClientGameResult();

        if (activeGame.getWinClientId() == null) {
            firstClientGameResult = getDeadHeatClientGameResult(activeGame, dbFirstClient);
            secondClientGameResult = getDeadHeatClientGameResult(activeGame, dbSecondClient);
        }

        if (activeGame.getWinClientId().equals(dbFirstClient.getId())) {
            firstClientGameResult = getWinClientGameResult(activeGame, dbFirstClient, dbSecondClient);
            secondClientGameResult = getLoseClientGameResult(activeGame, dbSecondClient);
        }

        if (activeGame.getWinClientId().equals(dbSecondClient.getId())) {
            firstClientGameResult = getLoseClientGameResult(activeGame, dbFirstClient);
            secondClientGameResult = getWinClientGameResult(activeGame, dbFirstClient, dbSecondClient);
        }

        dbFirstClient.addRating(firstClientGameResult.getRating());
        dbSecondClient.addRating(secondClientGameResult.getRating());

        final DbClosedGame closedGame = closedGameRepository.saveAndFlush(new DbClosedGame(activeGame.getCreateAt()));

        final DbClientGameResult dbFirstClientGameResult =
                new DbClientGameResult(
                        dbFirstClient, closedGame, firstClientGameResult.isWin(), firstClientGameResult.getRating());

        final DbClientGameResult dbSecondClientGameResult =
                new DbClientGameResult(
                        dbSecondClient, closedGame, secondClientGameResult.isWin(), secondClientGameResult.getRating());

        closedGame.addGameResults(Arrays.asList(dbFirstClientGameResult, dbSecondClientGameResult));

        activeGameHolder.deregisterActiveGame(activeGame.getId());

        return new ClosedGame(closedGame.getStartTime(), closedGame.getFinishTime(), firstClientGameResult, secondClientGameResult);
    }

    private ClientGameResult getWinClientGameResult(ActiveGame activeGame, DbClient winClient, DbClient loseClient) {

        final List<WarriorUpgrade> warriorUpgrades = addExperience(
                winClient.getHero(),
                warriorRepository.findAll(activeGame.getStartWarriorsByClientId(winClient.getId())),
                warriorRepository.findAll(activeGame.getStartWarriorsByClientId(loseClient.getId())));

        return new ClientGameResult().clientId(winClient.getId())
                .win()
                .rating(ratingByWin)
                .warriorUpgrades(warriorUpgrades);
    }

    private ClientGameResult getLoseClientGameResult(ActiveGame activeGame, DbClient client) {

        final List<WarriorUpgrade> warriorUpgrades = addExperience(
                client.getHero(),
                warriorRepository.findAll(activeGame.getStartWarriorsByClientId(client.getId())),
                warriorRepository.findAll(activeGame.getKilledWarriorIdsByClientId(client.getId())));

        return new ClientGameResult().clientId(client.getId())
                .rating(-ratingByWin)
                .warriorUpgrades(warriorUpgrades);
    }

    private ClientGameResult getDeadHeatClientGameResult(ActiveGame activeGame, DbClient client) {

        final List<WarriorUpgrade> warriorUpgrades = addExperience(
                client.getHero(),
                warriorRepository.findAll(activeGame.getStartWarriorsByClientId(client.getId())),
                warriorRepository.findAll(activeGame.getKilledWarriorIdsByClientId(client.getId())));

        return new ClientGameResult().clientId(client.getId())
                .warriorUpgrades(warriorUpgrades);
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

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException, CloseActiveGameException {
        return registerStepActiveGame(activeGame, null, null, null, null);
    }

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame, long currentClientId) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException, CloseActiveGameException {
        return registerStepActiveGame(activeGame, currentClientId, null, null, null);
    }

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame, long currentClientId, ClosedGame closedGame) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException, CloseActiveGameException {
        return registerStepActiveGame(activeGame, currentClientId, null, null, closedGame);
    }


    public StepActiveGame registerStepActiveGame(ActiveGame activeGame, Long currentClientId, Long attackWarriorId, Long defendingWarriorId) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException, CloseActiveGameException {
        return registerStepActiveGame(activeGame, currentClientId, attackWarriorId, defendingWarriorId, null);
    }

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame, Long currentClientId, Long attackWarriorId, Long defendingWarriorId, ClosedGame closedGame) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, ActiveGameDoesNotContainTwoClientsException, CloseActiveGameException {

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

        if (closedGame != null) {
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

}