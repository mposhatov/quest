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

    public ActiveGame createGame(Client firstClient, Client secondClient) throws ClientIsNotInTheQueueException, InvalidCurrentStepInQueueException {

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

    public DbClosedGame closeGame(long activeGameId) throws ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, InvalidCurrentStepInQueueException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);

        if (activeGame.getWinClients() == null || activeGame.getWinClients().size() < 1 || activeGame.getWinClients().size() > 2) {
            throw new ActiveGameDoesNotContainWinClientException(activeGame.getId());
        }

        if (activeGame.getFirstClient() == null || activeGame.getSecondClient() == null) {
            throw new ActiveGameDoesNotContainTwoClientsException(activeGame.getId());
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

        DbClosedGame closedGame = closedGameRepository.save(new DbClosedGame(activeGame.getCreateAt()));

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

        closedGameRepository.flush();

        return closedGame;
    }

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame) throws InvalidCurrentStepInQueueException {

        StepActiveGame stepActiveGame = EntityConverter.toStepActiveGame(activeGame);

        getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame.getFirstClient().getId(), stepActiveGame);
        getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame.getSecondClient().getId(), stepActiveGame);

        return stepActiveGame;
    }

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame, Long defendingWarriorId) throws ActiveGameDoesNotContainTwoClientsException, InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException {

        final List<Long> deadWarriors = new ArrayList<>();

        for (Warrior warrior : activeGame.getQueueWarriors()) {
            if (warrior.getWarriorCharacteristics().getHealth() == 0) {
                deadWarriors.add(warrior.getId());
            }
        }

        activeGame.registerDeadWarriors(deadWarriors);

        StepActiveGame stepActiveGame = EntityConverter.toStepActiveGame(activeGame);

        if (activeGame.getWinClients() != null && !activeGame.getWinClients().isEmpty()) {
            final DbClosedGame dbClosedGame = closeGame(activeGame.getId());
            stepActiveGame.setClosedGameId(dbClosedGame.getId());
        } else {
            activeGame.stepUp();
            stepActiveGame = EntityConverter.toStepActiveGame(activeGame)
                    .setAttackWarriorId(activeGame.getCurrentWarrior().getId())
                    .setDefendWarriorId(defendingWarriorId);
        }

        getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame.getFirstClient().getId(), stepActiveGame);
        getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame.getSecondClient().getId(), stepActiveGame);

        return stepActiveGame;
    }

    private DbHero addExperience(DbHero hero, List<DbWarrior> warriors, Long experience) {

        final long experienceByOne = experience / (warriors.size() + 1);

        hero = hero.addExperience(experienceByOne);

        DbHeroLevelRequirement heroLevelRequirement = heroLevelRequirementRepository.findOne(hero.getLevel() + 1);

        while (hero.getExperience() >= heroLevelRequirement.getRequirementExperience()) {
            hero = hero.upLevel();
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

}