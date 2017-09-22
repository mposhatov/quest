package com.mposhatov.service;

import com.mposhatov.ActiveGameSessionHolder;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Command;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.DbClient;
import com.mposhatov.exception.GameSessionDoesNotExistException;
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
    private ActiveGameSessionHolder activeGameSessionHolder;

    public ActiveGame createGame(DbClient dbClientFirstCommand, DbClient dbClientSecondCommand) {
        final Map<Command, Client> clientByCommand = new HashMap<>();

        final Client clientFirstCommand = EntityConverter.toClient(dbClientFirstCommand);
        final Client clientSecondCommand = EntityConverter.toClient(dbClientSecondCommand);

        clientByCommand.put(Command.COMMAND_1, clientFirstCommand);
        clientByCommand.put(Command.COMMAND_2, clientSecondCommand);

        final List<Warrior> warriors = new ArrayList<>();
        warriors.addAll(clientFirstCommand.getHero().getWarriors());
        warriors.addAll(clientSecondCommand.getHero().getWarriors());

        warriors.sort(Comparator.comparing(o -> o.getWarriorCharacteristics().getVelocity()));

        final Map<Long, Warrior> warriorByIds =
                warriors.stream().collect(Collectors.toMap(Warrior::getId, warrior -> warrior));

        final long gameSessionId = activeGameSessionHolder.generateGameSessionId();

        final ActiveGame activeGame = new ActiveGame(gameSessionId, clientByCommand, warriors, warriorByIds);

        activeGameSessionHolder.registerGameSession(activeGame, Arrays.asList(clientFirstCommand, clientSecondCommand));

        return activeGame;
    }

    public ActiveGame updateGame(long activeGameId, long attackingWarriorId, long defendingWarriorId) throws GameSessionDoesNotExistException {
        ActiveGame activeGame;
        try {
            activeGame = activeGameSessionHolder.getGameSessionById(activeGameId);
            final Warrior attackingWarrior = activeGame.getWarriors().get(attackingWarriorId);
            final Warrior defendingWarrior = activeGame.getWarriors().get(defendingWarriorId);

            //todo переделать
            defendingWarrior.takeDamage(attackingWarrior.giveDamage());

            activeGame.stepUp();

        } catch (GameSessionDoesNotExistException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }

        return activeGame;
    }

}
