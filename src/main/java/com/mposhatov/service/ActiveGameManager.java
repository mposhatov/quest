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
import java.util.stream.Collectors;

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
    private WarriorLevelRequirementRepository warriorLevelRequirementRepository;

    @Autowired
    private HeroLevelRequirementRepository heroLevelRequirementRepository;

    @Autowired
    private WarriorRepository warriorRepository;

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

        if (activeGame.getWinClients() == null || activeGame.getWinClients().size() < 1 || activeGame.getWinClients().size() > 2) {
            throw new ActiveGameDoesNotContainWinClientException(activeGame.getId());
        }

        final DbClient dbFirstClient = clientRepository.getOne(activeGame.getFirstClient().getId());
        final DbClient dbSecondClient = clientRepository.getOne(activeGame.getSecondClient().getId());

        final List<DbWarrior> firstClientWarriors = warriorRepository.findAll(
                activeGame.getFirstClient()
                        .getHero()
                        .getWarriors()
                        .stream()
                        .map(Warrior::getId)
                        .collect(Collectors.toList()));

        final List<DbWarrior> secondClientWarriors = warriorRepository.findAll(
                activeGame.getSecondClient()
                        .getHero()
                        .getWarriors()
                        .stream()
                        .map(Warrior::getId)
                        .collect(Collectors.toList()));

        addExperience(
                dbFirstClient.getHero(),
                firstClientWarriors,
                activeGame.getReceivedExperienceByClientId(dbFirstClient.getId()));

        addExperience(
                dbSecondClient.getHero(),
                secondClientWarriors,
                activeGame.getReceivedExperienceByClientId(dbSecondClient.getId()));

        DbClosedGame closedGame = closedGameRepository.saveAndFlush(new DbClosedGame(activeGame.getCreateAt()));

        final boolean firstClientWin = activeGame.getWinClients().contains(dbFirstClient.getId());
        final boolean secondClientWin = activeGame.getWinClients().contains(dbSecondClient.getId());

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

        final List<Long> deadWarriors = new ArrayList<>();

        for (Warrior warrior : activeGame.getQueueWarriors()) {
            if (warrior.getWarriorCharacteristics().getHealth() == 0) {
                deadWarriors.add(warrior.getId());
            }
        }

        activeGame.registerDeadWarriors(deadWarriors);

        StepActiveGame stepActiveGameFirstClient;

        if (activeGame.getWinClients() != null && !activeGame.getWinClients().isEmpty()) {
            stepActiveGameFirstClient = registerStepActiveGame(activeGame, false, null, null, true, currentClientId);
        } else {
            stepActiveGameFirstClient = registerStepActiveGame(activeGame, true, attackWarriorId, defendingWarriorId, false, currentClientId);
        }

        return stepActiveGameFirstClient;
    }

    public StepActiveGame registerStepClosingActiveGame(ActiveGame activeGame) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException {
        return registerStepActiveGame(activeGame, false, null, null, true, null);
    }

    public StepActiveGame registerStepClosingActiveGame(ActiveGame activeGame, long currentClientId) throws InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException {
        return registerStepActiveGame(activeGame, false, null, null, true, currentClientId);
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

        if(currentClientId != null) {
            if(currentClientId == activeGame.getFirstClient().getId()) {
                getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame.getSecondClient().getId(), stepActiveGameSecondClient);
                return stepActiveGameFirstClient;
            } else if(currentClientId == activeGame.getSecondClient().getId()) {
                getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame.getFirstClient().getId(), stepActiveGameFirstClient);
                return stepActiveGameSecondClient;
            }
        }

        getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame.getFirstClient().getId(), stepActiveGameFirstClient);
        getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame.getSecondClient().getId(), stepActiveGameSecondClient);

        return stepActiveGameFirstClient;
    }

    private DbHero addExperience(DbHero hero, List<DbWarrior> warriors, Long experience) {

        final long experienceByOne = experience / (warriors.size() + 1);

        hero = hero.addExperience(experienceByOne);

        DbHeroLevelRequirement heroLevelRequirement = heroLevelRequirementRepository.findOne(hero.getLevel() + 1);

        while (hero.getExperience() >= heroLevelRequirement.getRequirementExperience()) {
            hero = hero.upLevel(heroLevelRequirement.getAdditionalHeroPoint());
            heroLevelRequirement = heroLevelRequirementRepository.findOne(hero.getLevel() + 1);
        }

        for (DbWarrior warrior : warriors) {
            warrior = warrior.addExperience(experienceByOne);

            DbWarriorLevelRequirement warriorLevelRequirement = warriorLevelRequirementRepository
                    .findByWarriorNameAndLevel(warrior.getWarriorDescription().getName(), warrior.getLevel() + 1);

            while (warrior.getExperience() >= warriorLevelRequirement.getRequirementExperience()) {
                warrior = warrior.upLevel(warriorLevelRequirement.getAdditionalWarriorCharacteristics());
                warriorLevelRequirement = warriorLevelRequirementRepository
                        .findByWarriorNameAndLevel(warrior.getWarriorDescription().getName(), warrior.getLevel() + 1);
            }
        }

        return hero;
    }

//    public boolean isPossibleStrike(Warrior attackWarrior, Warrior defendWarrior) {
//        boolean acces = false;
//
//        if(attackWarrior.getRangeType().equals(RangeType.RANGE)) {
//            acces = true;
//        } else {
//
//            if(attackWarrior.){
//
//
//            if(attackWarrior.getPosition() >= 1 && attackWarrior.getPosition() <= 7 &&
//                    defendWarrior.getPosition() >= 1 && defendWarrior.getPosition() <= 7) {
//                acces = true;
//            } else if() {
//
//            }
//        }
//
//        return false;
//    }

}