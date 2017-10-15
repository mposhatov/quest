package com.mposhatov.service;

import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.Command;
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGameSearchRequestHolder;
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

    public ActiveGame createGame(Client firstCommand, Client secondCommand) throws ClientIsNotInTheQueueException {

        final Map<Command, Client> clientByCommands = new HashMap<>();

        clientByCommands.put(Command.COMMAND_1, firstCommand);
        clientByCommands.put(Command.COMMAND_2, secondCommand);

        final List<Warrior> queueWarriors = new ArrayList<>();
        final Map<Long, Warrior> warriorByIds = new HashMap<>();

        firstCommand.getHero().getWarriors().forEach(warrior -> {
            warrior.setCommand(Command.COMMAND_1);
            firstCommand.getHero().setCommand(Command.COMMAND_1);
            warriorByIds.put(warrior.getId(), warrior);
            queueWarriors.add(warrior);
        });

        secondCommand.getHero().getWarriors().forEach(warrior -> {
            warrior.setCommand(Command.COMMAND_2);
            secondCommand.getHero().setCommand(Command.COMMAND_2);
            warriorByIds.put(warrior.getId(), warrior);
            queueWarriors.add(warrior);
        });

        queueWarriors.sort(Comparator.comparing(o -> o.getWarriorCharacteristics().getVelocity()));

        final ActiveGame activeGame = new ActiveGame(
                activeGameHolder.generateActiveGameId(), clientByCommands, queueWarriors, warriorByIds);

        activeGameHolder.registerActiveGame(activeGame, firstCommand, secondCommand);

        return activeGame;
    }
}