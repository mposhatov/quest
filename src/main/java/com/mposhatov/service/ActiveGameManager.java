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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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

        final List<Warrior> queueWarriors = new ArrayList<>();

        queueWarriors.addAll(firstClient.getHero().getWarriors());
        queueWarriors.addAll(secondClient.getHero().getWarriors());

        queueWarriors.sort(Comparator.comparing(o -> o.getWarriorCharacteristics().getVelocity()));

        final ActiveGame activeGame =
                new ActiveGame(activeGameHolder.generateActiveGameId(), firstClient, secondClient, queueWarriors);

        activeGameHolder.registerActiveGame(activeGame);

        final StepActiveGame stepActiveGame = EntityConverter.toStepActiveGame(activeGame);

        getUpdatedActiveGameProcessor.registerStepActiveGame(firstClient.getId(), stepActiveGame);
        getUpdatedActiveGameProcessor.registerStepActiveGame(secondClient.getId(), stepActiveGame);

        return activeGame;
    }

    public DbClosedGame closeGame(long activeGameId) throws ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, InvalidCurrentStepInQueueException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);
        final List<Long> winClients = activeGame.getWinClients();

        if (winClients == null) {
            throw new ActiveGameDoesNotContainWinClientException(activeGame.getId());
        }

        final Client firstClient = activeGame.getClients().get(0);
        final Client secondClient = activeGame.getClients().get(1);

        if (firstClient == null || secondClient == null || activeGame.getClients().size() != 2) {
            throw new ActiveGameDoesNotContainTwoClientsException(activeGame.getId());
        }

        DbClosedGame closedGame = closedGameRepository.save(new DbClosedGame(activeGame.getCreateAt()));

        final DbClient dbFirstClient = clientRepository.getOne(firstClient.getId());
        final DbClient dbSecondClient = clientRepository.getOne(secondClient.getId());

        addExperience(dbFirstClient.getHero(), warriorRepository.findAll(
                activeGame.getClientById(dbFirstClient.getId())
                        .getHero().getWarriors()
                        .stream()
                        .map(Warrior::getId)
                        .collect(Collectors.toList())),
                activeGame.getReceivedExperienceByClientId(dbFirstClient.getId()));

        addExperience(dbSecondClient.getHero(), warriorRepository.findAll(
                activeGame.getClientById(dbSecondClient.getId())
                        .getHero().getWarriors()
                        .stream()
                        .map(Warrior::getId)
                        .collect(Collectors.toList())),
                activeGame.getReceivedExperienceByClientId(dbSecondClient.getId()));

        final boolean firstClientWin = winClients.size() == 1 && winClients.contains(dbFirstClient.getId());
        final boolean secondClientWin = winClients.size() == 1 && winClients.contains(dbSecondClient.getId());

        final long firstClientAddRating = firstClientWin ? 5 : -5;
        final long secondClientAddRation = secondClientWin ? 5 : -5;

        dbFirstClient.addRating(firstClientAddRating);
        dbSecondClient.addRating(secondClientAddRation);

        DbClientGameResult firstClientGameResult =
                new DbClientGameResult(dbFirstClient, closedGame, firstClientWin, firstClientAddRating);

        DbClientGameResult secondClientGameResult =
                new DbClientGameResult(dbSecondClient, closedGame, secondClientWin, secondClientAddRation);

        closedGame.addGameResults(Arrays.asList(firstClientGameResult, secondClientGameResult));

        activeGameHolder.deregisterActiveGame(activeGame.getId());

        closedGameRepository.flush();

        return closedGame;
    }

    private DbHero addExperience(DbHero hero, List<DbWarrior> warriors, Long experience) {

        final long experienceByOne = experience / (warriors.size() + 1);

        hero = hero.addExperience(experienceByOne);

        while (hero.getExperience() >=
                heroLevelRequirementRepository.findOne(hero.getLevel() + 1).getRequirementExperience()) {
            hero = hero.upLevel();
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