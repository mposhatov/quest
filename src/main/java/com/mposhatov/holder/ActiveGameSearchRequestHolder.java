package com.mposhatov.holder;

import com.mposhatov.dto.Client;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveGameSearchRequestHolder {

    private Map<Long, ActiveGameSearchRequest> requestsByClientIds = new ConcurrentHashMap<>();

    public void registerGameSearchRequest(Client client) {
        requestsByClientIds.put(client.getId(), new ActiveGameSearchRequest(client));
    }

    public void deregisterGameSearchRequestByClientId(long clientId) throws ClientIsNotInTheQueueException {

        ActiveGameSearchRequest request = requestsByClientIds.get(clientId);

        if (request == null) {
            throw new ClientIsNotInTheQueueException(clientId);
        }

        requestsByClientIds.remove(clientId);
    }

    public List<ActiveGameSearchRequest> getRequests() {
        return new ArrayList<>(requestsByClientIds.values());
    }

    public boolean existByClientId(long clientId) {
        return requestsByClientIds.get(clientId) != null;
    }
}

