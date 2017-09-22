package com.mposhatov;

import com.mposhatov.dto.Client;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import com.mposhatov.exception.GameSessionDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveGameSessionHolder {

    private final Logger logger = LoggerFactory.getLogger(ActiveGameSessionHolder.class);

    private Map<Long, Long> gameSessionIdByClientIds = new ConcurrentHashMap<>(new HashMap<>());
    private Map<Long, ActiveGame> gameSessionByGameSessionIds = new ConcurrentHashMap<>(new HashMap<>());

    public void registerGameSession(ActiveGame activeGame, List<Client> clients) {
        gameSessionByGameSessionIds.put(activeGame.getId(), activeGame);
        clients.forEach(client -> gameSessionIdByClientIds.put(client.getId(), activeGame.getId()));
    }

    public void deregisterGameSession(long gameSessionId) throws GameSessionDoesNotExistException {
        try {
            getGameSessionById(gameSessionId)
                    .getClientByCommands()
                    .forEach((command, client) -> gameSessionIdByClientIds.remove(client.getId()));
        } catch (GameSessionDoesNotExistException e) {
            logger.error(e.getMessage());
            throw e;
        }

        gameSessionByGameSessionIds.remove(gameSessionId);
    }

    public ActiveGame getGameSessionById(long gameSessionId) throws GameSessionDoesNotExistException {
        final ActiveGame activeGame = gameSessionByGameSessionIds.get(gameSessionId);
        if (activeGame == null) {
            throw new GameSessionDoesNotExistException(gameSessionId);
        } else {
            return activeGame;
        }
    }

    public ActiveGame getGameSessionByClientId(long clientId) throws ClientIsNotInTheQueueException {
        final Long gameSessionId = gameSessionIdByClientIds.get(clientId);
        if(gameSessionId != null) {
            return gameSessionByGameSessionIds.get(gameSessionId);
        }
        else {
            throw new ClientIsNotInTheQueueException(clientId);
        }
    }

    public long generateGameSessionId() {
        return gameSessionByGameSessionIds.size() + 1;
    }
}
