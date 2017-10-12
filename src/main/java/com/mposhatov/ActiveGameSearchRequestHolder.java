package com.mposhatov;

import com.mposhatov.dto.Client;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

@Component
public class ActiveGameSearchRequestHolder {

    private Map<Long, ActiveGameSearchRequest> gameSearchRequestsByClientIds = new ConcurrentSkipListMap<>(new Comparator<Long>() {
        @Override
        public int compare(Long cl1, Long cl2) {
            return (int) (gameSearchRequestsByClientIds.get(cl1).getClient().getRate() - gameSearchRequestsByClientIds.get(cl2).getClient().getRate());
        }
    });

    public void registerGameSearchRequest(Client client) {
        this.gameSearchRequestsByClientIds.put(client.getId(), new ActiveGameSearchRequest(client));
    }

    public void deregisterGameSearchRequest(Long clientId) {
        this.gameSearchRequestsByClientIds.remove(clientId);
    }

    private ActiveGameSearchRequest getByClientId(long clientId) throws ClientIsNotInTheQueueException {

        final ActiveGameSearchRequest gameSearchRequest = gameSearchRequestsByClientIds.get(clientId);

        if (gameSearchRequest == null) {
            throw new ClientIsNotInTheQueueException(clientId);
        }

        return gameSearchRequest;
    }

    public Iterator<Map.Entry<Long, ActiveGameSearchRequest>> getIterator() {
        return gameSearchRequestsByClientIds.entrySet().iterator();
    }

    public boolean existByClientId(Long clientId) {
        return gameSearchRequestsByClientIds.get(clientId) != null;
    }

}

