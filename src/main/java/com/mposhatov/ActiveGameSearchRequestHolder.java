package com.mposhatov;

import com.mposhatov.dto.ActiveGameSearchRequest;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveGameSearchRequestHolder {

    private Map<Long, ActiveGameSearchRequest> gameSearchRequestsByClientIds = new ConcurrentHashMap<>();

    public void registerGameSearchRequest(ActiveGameSearchRequest gameSearchRequest) {
        this.gameSearchRequestsByClientIds.put(gameSearchRequest.getClientId(), gameSearchRequest);
    }

    public void deregisterGameSearchRequest(long clientId) {
        this.gameSearchRequestsByClientIds.remove(clientId);
    }

    private ActiveGameSearchRequest getByClientId(long clientId) throws ClientIsNotInTheQueueException {

        final ActiveGameSearchRequest gameSearchRequest = gameSearchRequestsByClientIds.get(clientId);

        if (gameSearchRequest == null) {
            throw new ClientIsNotInTheQueueException(clientId);
        }

        return gameSearchRequest;
    }

    public boolean existByClientId(long clientId) {
        return gameSearchRequestsByClientIds.get(clientId) != null;
    }

}
