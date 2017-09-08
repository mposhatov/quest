package com.mposhatov;

import com.mposhatov.dto.GameSession;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameSessionHolder {
    private Map<Long, GameSession> gameSessionByClientIds = new ConcurrentHashMap<>(new HashMap<>());

    public void registerGameSession(long clientId, GameSession gameSession) {
        gameSessionByClientIds.put(clientId, gameSession);
    }

    public void deregisterClientSession(long clientId) {
        gameSessionByClientIds.remove(clientId);
    }

    public GameSession getGameSession(long clientId) {
        return gameSessionByClientIds.get(clientId);
    }

    public long getGameSessionId() {
        return gameSessionByClientIds.size() + 1;
    }
}
