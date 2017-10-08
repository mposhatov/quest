package com.mposhatov;

import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.Client;
import com.mposhatov.exception.ActiveGameDoesNotExistException;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveGameHolder {

    private final Logger logger = LoggerFactory.getLogger(ActiveGameHolder.class);

    private Map<Long, Long> activeGameIdByClientIds = new ConcurrentHashMap<>(new HashMap<>());
    private Map<Long, ActiveGame> activeGameByGameSessionIds = new ConcurrentHashMap<>(new HashMap<>());

    public void registerActiveGame(ActiveGame activeGame, List<Client> clients) {
        activeGameByGameSessionIds.put(activeGame.getId(), activeGame);
        clients.forEach(client -> activeGameIdByClientIds.put(client.getId(), activeGame.getId()));
    }

    public void deregisterActiveGame(long activeGameId) throws ActiveGameDoesNotExistException {

        getActiveGameById(activeGameId)
                .getClientByCommands()
                .forEach((command, client) -> activeGameIdByClientIds.remove(client.getId()));

        activeGameByGameSessionIds.remove(activeGameId);
    }

    public ActiveGame getActiveGameById(long activeGameId) throws ActiveGameDoesNotExistException {

        final ActiveGame activeGame = activeGameByGameSessionIds.get(activeGameId);

        if (activeGame == null) {
            throw new ActiveGameDoesNotExistException(activeGameId);
        }

        return activeGame;
    }

    public ActiveGame getActiveGameByClientId(long clientId) throws ClientIsNotInTheQueueException, ActiveGameDoesNotExistException {

        final Long activeGameId = activeGameIdByClientIds.get(clientId);

        if (activeGameId == null) {
            throw new ClientIsNotInTheQueueException(clientId);
        }

        final ActiveGame activeGame = activeGameByGameSessionIds.get(activeGameId);

        if (activeGame == null) {
            throw new ActiveGameDoesNotExistException(activeGameId);
        }

        return activeGame;
    }

    public long getActiveGameIdByClientId(long clientId) throws ClientIsNotInTheQueueException {

        final Long activeGameId = activeGameIdByClientIds.get(clientId);

        if (activeGameId == null) {
            throw new ClientIsNotInTheQueueException(clientId);
        }

        return activeGameId;

    }

    public boolean existByClientId(long clientId) {
        return activeGameIdByClientIds.get(clientId) != null;
    }

    public long generateActiveGameId() {
        return activeGameByGameSessionIds.size() + 1;
    }
}
