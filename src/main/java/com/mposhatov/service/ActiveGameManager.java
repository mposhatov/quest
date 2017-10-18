package com.mposhatov.service;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.ClosedGameRepository;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.Command;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbClientGameResult;
import com.mposhatov.entity.DbClosedGame;
import com.mposhatov.exception.ActiveGameDoesNotContainCommandsException;
import com.mposhatov.exception.ActiveGameDoesNotExistException;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import com.mposhatov.exception.GetUpdateActiveGameRequestDoesNotExistException;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.request.GetUpdateActiveGameProcessor;
import com.mposhatov.request.GetUpdateActiveGameRequest;
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
    private GetUpdateActiveGameProcessor getUpdateActiveGameProcessor;

    @Autowired
    private ClosedGameRepository closedGameRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ActiveGame createGame(Client firstCommand, Client secondCommand) throws ClientIsNotInTheQueueException {

        final Map<Command, Client> clientByCommands = new HashMap<>();

        clientByCommands.put(Command.COMMAND_1, firstCommand);
        clientByCommands.put(Command.COMMAND_2, secondCommand);

        final List<Warrior> queueWarriors = new ArrayList<>();
        final Map<Long, Warrior> warriorByIds = new HashMap<>();

        for (Warrior warrior : firstCommand.getHero().getWarriors()) {
            warrior.setCommand(Command.COMMAND_1);
            firstCommand.getHero().setCommand(Command.COMMAND_1);
            warriorByIds.put(warrior.getId(), warrior);
            queueWarriors.add(warrior);
        }

        for (Warrior warrior : secondCommand.getHero().getWarriors()) {
            warrior.setCommand(Command.COMMAND_2);
            secondCommand.getHero().setCommand(Command.COMMAND_2);
            warriorByIds.put(warrior.getId(), warrior);
            queueWarriors.add(warrior);
        }

        queueWarriors.sort(Comparator.comparing(o -> o.getWarriorCharacteristics().getVelocity()));

        final ActiveGame activeGame = new ActiveGame(
                activeGameHolder.generateActiveGameId(), clientByCommands, queueWarriors, warriorByIds).update();

        activeGameHolder.registerActiveGame(activeGame, firstCommand, secondCommand);

        return activeGame;
    }

    public DbClosedGame closeGame(long activeGameId) throws ActiveGameDoesNotExistException, ActiveGameDoesNotContainCommandsException, GetUpdateActiveGameRequestDoesNotExistException {
        final ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);

        for (Client client : activeGame.getClients()) {
            if (getUpdateActiveGameProcessor.existByClientId(client.getId())) {

                final GetUpdateActiveGameRequest getUpdateActiveGameRequest =
                        getUpdateActiveGameProcessor.deregisterRequest(client.getId());

                getUpdateActiveGameRequest.setNoContent();
            }
        }

        activeGameHolder.deregisterActiveGame(activeGame.getId());

        DbClosedGame closedGame = new DbClosedGame(activeGame.getCreateAt());

        closedGame = closedGameRepository.save(closedGame);

        final DbClient firstClient =
                clientRepository.getOne(activeGame.getClientByCommand(Command.COMMAND_1).getId());

        final DbClient secondClient =
                clientRepository.getOne(activeGame.getClientByCommand(Command.COMMAND_1).getId());

        DbClientGameResult firstClientGameResult = null;
        DbClientGameResult secondClientGameResult = null;

        if (activeGame.isWin(Command.COMMAND_1)) {
            firstClient.addRating(5);
            secondClient.addRating(-5);
            firstClientGameResult = new DbClientGameResult(closedGame, firstClient, true, 5);
            secondClientGameResult = new DbClientGameResult(closedGame, secondClient, false, -5);
        } else if (activeGame.isWin(Command.COMMAND_2)) {
            firstClient.addRating(-5);
            secondClient.addRating(5);
            firstClientGameResult = new DbClientGameResult(closedGame, firstClient, false, -5);
            secondClientGameResult = new DbClientGameResult(closedGame, secondClient, true, 5);
        }

        closedGame.addGameResults(Arrays.asList(firstClientGameResult, secondClientGameResult));

        closedGameRepository.flush();

        return closedGame;
    }
}