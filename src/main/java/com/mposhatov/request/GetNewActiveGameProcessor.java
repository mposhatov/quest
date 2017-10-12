package com.mposhatov.request;

import com.mposhatov.ActiveGameHolder;
import com.mposhatov.ActiveGameSearchRequestHolder;
import com.mposhatov.exception.ActiveGameDoesNotExistException;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GetNewActiveGameProcessor {

    private Map<Long, GetNewActiveGameRequest> requestByClientIds = new ConcurrentHashMap<>();

    public void registerRequest(long clientId, GetNewActiveGameRequest request) {
        this.requestByClientIds.put(clientId, request);
    }

    public void deregisterRequest(long clientId) {
        this.requestByClientIds.remove(clientId);
    }

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Scheduled(fixedDelay = 100)
    public void processRequests() throws ActiveGameDoesNotExistException, ClientIsNotInTheQueueException {

        final Iterator<Map.Entry<Long, GetNewActiveGameRequest>> requestIterator =
                requestByClientIds.entrySet().iterator();

        while (requestIterator.hasNext()) {
            final Map.Entry<Long, GetNewActiveGameRequest> entry = requestIterator.next();

            final GetNewActiveGameRequest request = entry.getValue();

            final long clientId = request.getClientId();

            if (activeGameHolder.existByClientId(clientId)) {
                request.setResult(activeGameHolder.getActiveGameByClientId(clientId));
                requestIterator.remove();
            }

        }

    }
}
