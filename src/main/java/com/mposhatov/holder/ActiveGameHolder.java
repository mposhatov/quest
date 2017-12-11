package com.mposhatov.holder;

import com.mposhatov.exception.ActiveGameException;
import com.mposhatov.exception.ClientException;
import com.mposhatov.service.validator.ActiveGameExceptionThrower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveGameHolder {

    @Autowired
    private ActiveGameExceptionThrower activeGameExceptionThrower;

    private Map<Long, Long> activeGameIdByClientIds = new ConcurrentHashMap<>();
    private Map<Long, ActiveGame> activeGameByIds = new ConcurrentHashMap<>();

    public void registerActiveGame(ActiveGame activeGame) {

        activeGameByIds.put(activeGame.getId(), activeGame);

        activeGameIdByClientIds.put(activeGame.getFirstClient().getId(), activeGame.getId());
        activeGameIdByClientIds.put(activeGame.getSecondClient().getId(), activeGame.getId());
    }

    public void deregisterActiveGame(long activeGameId) throws ActiveGameException.DoesNotExist, ActiveGameException.DoesNotContainTwoClients {

        final ActiveGame activeGame = getActiveGameById(activeGameId);

        activeGameExceptionThrower.throwExceptionIfActiveGameDoesNotContainTwoClients(activeGame);

        activeGameIdByClientIds.remove(activeGame.getFirstClient().getId());
        activeGameIdByClientIds.remove(activeGame.getSecondClient().getId());

        activeGameByIds.remove(activeGameId);
    }

    public ActiveGame getActiveGameByClientId(long clientId) throws ClientException.HasNotActiveGame, ActiveGameException.DoesNotExist {

        return getActiveGameById(getActiveGameIdByClientId(clientId));
    }

    public ActiveGame getActiveGameById(long activeGameId) throws ActiveGameException.DoesNotExist {

        final ActiveGame activeGame = activeGameByIds.get(activeGameId);

        if (activeGame == null) {
            throw new ActiveGameException.DoesNotExist(activeGameId);
        }

        return activeGame;
    }

    public long getActiveGameIdByClientId(long clientId) throws ClientException.HasNotActiveGame {

        final Long activeGameId = activeGameIdByClientIds.get(clientId);

        if (activeGameId == null) {
            throw new ClientException.HasNotActiveGame(clientId);
        }

        return activeGameId;

    }

    public boolean existByClientId(long clientId) {
        return activeGameIdByClientIds.get(clientId) != null;
    }

    public long generateActiveGameId() {
        return activeGameByIds.size() + 1;
    }

    public List<ActiveGame> getActiveGames() {
        return new ArrayList<>(activeGameByIds.values());
    }
}
