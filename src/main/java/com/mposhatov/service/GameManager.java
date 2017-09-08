package com.mposhatov.service;

import com.mposhatov.GameSessionHolder;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Command;
import com.mposhatov.dto.GameSession;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.DbClient;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameManager {

    @Autowired
    private GameSessionHolder gameSessionHolder;

    public GameSession createGame(DbClient dbClient1, DbClient dbClient2) {
        final Map<Command, Client> clientByCommand = new HashMap<>();

        final Client client1 = EntityConverter.toClient(dbClient1);
        final Client client2 = EntityConverter.toClient(dbClient2);

        clientByCommand.put(Command.COMMAND_1, client1);
        clientByCommand.put(Command.COMMAND_2, client2);

        final List<Warrior> warriors = new LinkedList<>();
        warriors.addAll(client1.getHero().getWarriors());
        warriors.addAll(client2.getHero().getWarriors());

        warriors.sort(Comparator.comparing(o -> o.getWarriorCharacteristics().getVelocity()));

        final Map<String, Warrior> warriorByNames =
                warriors.stream().collect(Collectors.toMap(Warrior::getName, warrior -> warrior));

        final long gameSessionId = gameSessionHolder.getGameSessionId();

        final GameSession gameSession = new GameSession(gameSessionId, clientByCommand, warriors, warriorByNames);

        gameSessionHolder.registerGameSession(gameSessionId, gameSession);

        return gameSession;
    }

}
