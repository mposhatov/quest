package com.mposhatov.service;

import com.mposhatov.ActiveGameHolder;
import com.mposhatov.dao.WarriorRepository;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.Command;
import com.mposhatov.entity.DbClient;
import com.mposhatov.exception.*;
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
    private FightSimulator fightSimulator;

    public ActiveGame createGame(Client firstCommand, Client secondCommand) {

        final Map<Command, Client> clientByCommands = new HashMap<>();

        clientByCommands.put(Command.COMMAND_1, firstCommand);
        clientByCommands.put(Command.COMMAND_2, secondCommand);

        final List<Warrior> queueWarriors = new ArrayList<>();
        final Map<Long, Warrior> warriorByIds = new HashMap<>();

        warriorRepository.findMainByHero(firstCommand.getHero()).forEach(dbWarrior -> {
            final Warrior warrior = EntityConverter.toWarrior(dbWarrior).setCommand(Command.COMMAND_1);
            firstCommand.getHero().setCommand(Command.COMMAND_1).getWarriors().add(warrior);
            warriorByIds.put(warrior.getId(), warrior);
            queueWarriors.add(warrior);
        });

        warriorRepository.findMainByHero(dbSecondCommand.getHero()).forEach(dbWarrior -> {
            final Warrior warrior = EntityConverter.toWarrior(dbWarrior).setCommand(Command.COMMAND_2);
            secondCommand.getHero().setCommand(Command.COMMAND_2).getWarriors().add(warrior);
            warriorByIds.put(warrior.getId(), warrior);
            queueWarriors.add(warrior);
        });

        queueWarriors.sort(Comparator.comparing(o -> o.getWarriorCharacteristics().getVelocity()));

        final ActiveGame activeGame =
                new ActiveGame(activeGameHolder.generateActiveGameId(), clientByCommands, queueWarriors, warriorByIds);

        activeGameHolder.registerActiveGame(activeGame, firstCommand, secondCommand);

        return activeGame;
    }

    public ActiveGame directAttack(long activeGameId, long defendingWarriorId)
            throws ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, BlowToAllyException, ActiveGameDoesNotContainCommandsException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);

        final Warrior attackWarrior = activeGame.getCurrentWarrior();
        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);

        if (attackWarrior.getCommand().equals(defendingWarrior.getCommand())) {
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