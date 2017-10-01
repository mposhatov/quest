package com.mposhatov.service;

import com.mposhatov.ActiveGameHolder;
import com.mposhatov.dao.WarriorRepository;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Command;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbWarrior;
import com.mposhatov.exception.*;
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
    private FightSimulator fightSimulator;

    @Autowired
    private WarriorRepository warriorRepository;

    public ActiveGame createGame(DbClient dbClientFirstCommand, DbClient dbClientSecondCommand) {
        final Map<Command, Client> clientByCommand = new HashMap<>();

        final Client clientFirstCommand = EntityConverter.toClient(dbClientFirstCommand);
        final Client clientSecondCommand = EntityConverter.toClient(dbClientSecondCommand);

        clientByCommand.put(Command.COMMAND_1, clientFirstCommand);
        clientByCommand.put(Command.COMMAND_2, clientSecondCommand);

        final List<Warrior> warriors = new ArrayList<>();

        final List<DbWarrior> dbMainWarriorsFirstClient = warriorRepository.findMainByHero(dbClientFirstCommand.getHero());
        final List<DbWarrior> dbMainWarriorsSecondClient = warriorRepository.findMainByHero(dbClientSecondCommand.getHero());

        final List<Warrior> warriorsFirstCommand =
                dbMainWarriorsFirstClient.stream().map(EntityConverter::toWarrior).collect(Collectors.toList());

        final List<Warrior> warriorsSecondCommand =
                dbMainWarriorsSecondClient.stream().map(EntityConverter::toWarrior).collect(Collectors.toList());

        clientFirstCommand.getHero().setWarriors(warriorsFirstCommand);
        clientSecondCommand.getHero().setWarriors(warriorsSecondCommand);

        warriorsFirstCommand.forEach(w -> w.setCommand(Command.COMMAND_1));
        warriorsSecondCommand.forEach(w -> w.setCommand(Command.COMMAND_2));

        warriors.addAll(warriorsFirstCommand);
        warriors.addAll(warriorsSecondCommand);

        warriors.sort(Comparator.comparing(o -> o.getWarriorCharacteristics().getVelocity()));

        final Map<Long, Warrior> warriorByIds =
                warriors.stream().collect(Collectors.toMap(Warrior::getId, warrior -> warrior));

        final long activeGameId = activeGameHolder.generateActiveGameId();

        final ActiveGame activeGame = new ActiveGame(activeGameId, clientByCommand, warriors, warriorByIds);

        activeGameHolder.registerActiveGame(activeGame, Arrays.asList(clientFirstCommand, clientSecondCommand));

        return activeGame;
    }

    public ActiveGame directAttack(long activeGameId, long defendingWarriorId)
            throws ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, BlowToAllyException, ActiveGameDoesNotContainCommandsException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);

        final Warrior attackWarrior = activeGame.getCurrentWarrior();
        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);

        final Command command = defendingWarrior.getCommand();

        if (attackWarrior.getCommand().equals(command)) {
            throw new BlowToAllyException(attackWarrior.getId(), defendingWarrior.getId());
        }

        fightSimulator.directionAttack(
                attackWarrior.getWarriorCharacteristics(), defendingWarrior.getWarriorCharacteristics());

        if (defendingWarrior.isDead()) {
            activeGame.registerDeadWarrior(defendingWarrior);
            if (!activeGame.isWin(attackWarrior.getCommand())) {
                activeGame.stepUp();
            }
        }

        return activeGame;
    }
}