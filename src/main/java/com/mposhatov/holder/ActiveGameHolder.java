package com.mposhatov.holder;

import com.mposhatov.dto.Client;
import com.mposhatov.exception.ActiveGameDoesNotExistException;
import com.mposhatov.exception.ClientHasNotActiveGameException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveGameHolder {

    private Map<Long, Long> activeGameIdByClientIds = new ConcurrentHashMap<>();
    private Map<Long, ActiveGame> activeGameByIds = new ConcurrentHashMap<>();

    public void registerActiveGame(ActiveGame activeGame) {

        activeGameByIds.put(activeGame.getId(), activeGame);

        for (Client client : activeGame.getClients()) {
            activeGameIdByClientIds.put(client.getId(), activeGame.getId());
        }
    }

    public void deregisterActiveGame(long activeGameId) throws ActiveGameDoesNotExistException {
        getActiveGameById(activeGameId)
                .getClients()
                .forEach(client -> activeGameIdByClientIds.remove(client.getId()));

        activeGameByIds.remove(activeGameId);
    }

    public ActiveGame getActiveGameById(long activeGameId) throws ActiveGameDoesNotExistException {

        final ActiveGame activeGame = activeGameByIds.get(activeGameId);

        if (activeGame == null) {
            throw new ActiveGameDoesNotExistException(activeGameId);
        }

        return activeGame;
    }

    public ActiveGame getActiveGameByClientId(long clientId) throws ActiveGameDoesNotExistException, ClientHasNotActiveGameException {

        final Long activeGameId = activeGameIdByClientIds.get(clientId);

        if (activeGameId == null) {
            throw new ClientHasNotActiveGameException(clientId);
        }

        final ActiveGame activeGame = activeGameByIds.get(activeGameId);

        if (activeGame == null) {
            throw new ActiveGameDoesNotExistException(activeGameId);
        }

        return activeGame;
    }

    public long getActiveGameIdByClientId(long clientId) throws ClientHasNotActiveGameException {

        final Long activeGameId = activeGameIdByClientIds.get(clientId);

        if (activeGameId == null) {
            throw new ClientHasNotActiveGameException(clientId);
        }

        return activeGameId;

    }

    public boolean existByClientId(long clientId) {
        return activeGameIdByClientIds.get(clientId) != null;
    }

    public long generateActiveGameId() {
        return activeGameByIds.size() + 1;
    }
}
