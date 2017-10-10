package com.mposhatov.request;

import com.mposhatov.exception.ClientIsNotInTheQueueException;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class RequestHolder {

    private Map<Long, Request> requestByClientIds = new ConcurrentHashMap<>();

    public void registerRequest(Request request) {
        this.requestByClientIds.put(request.getClientId(), request);
    }

    public void deregisterGetNewActiveGameRequest(long clientId) {
        this.requestByClientIds.remove(clientId);
    }

    public Request getRequestByClientId(long clientId) throws ClientIsNotInTheQueueException {

        final Request request = requestByClientIds.get(clientId);

        if (request == null) {
            throw new ClientIsNotInTheQueueException(clientId);
        }

        return request;
    }

    public Map<Long, Request> getRequests() {
        return requestByClientIds;
    }

}
