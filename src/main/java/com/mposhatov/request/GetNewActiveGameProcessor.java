package com.mposhatov.request;

import com.mposhatov.dto.ActiveGame;
import com.mposhatov.exception.ActiveGameDoesNotExistException;
import com.mposhatov.exception.ClientHasNotActiveGameException;
import com.mposhatov.exception.GetNewActiveGameRequestDoesNotExistException;
import com.mposhatov.holder.ActiveGameHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GetNewActiveGameProcessor {

    private Map<Long, GetNewActiveGameRequest> requestByClientIds = new ConcurrentHashMap<>();

    public DeferredResult<ActiveGame> registerRequest(long clientId) {

        final GetNewActiveGameRequest getNewActiveGameRequest = new GetNewActiveGameRequest(clientId);

        this.requestByClientIds.put(clientId, getNewActiveGameRequest);

        return getNewActiveGameRequest.getDeferredResult();
    }

    public void deregisterRequest(long clientId) throws GetNewActiveGameRequestDoesNotExistException {

        final GetNewActiveGameRequest request = this.requestByClientIds.get(clientId);

        if (request == null) {
            throw new GetNewActiveGameRequestDoesNotExistException(clientId);
        }

        this.requestByClientIds.remove(clientId);
    }

    public boolean existByClientId(long clientId) {
        return this.requestByClientIds.get(clientId) != null;
    }

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Scheduled(fixedDelay = 100)
    public void processRequests() throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, GetNewActiveGameRequestDoesNotExistException {

        for (Map.Entry<Long, GetNewActiveGameRequest> entry : requestByClientIds.entrySet()) {

            final GetNewActiveGameRequest request = entry.getValue();

            final long clientId = request.getClientId();

            if (activeGameHolder.existByClientId(clientId) && existByClientId(clientId)) {
                request.setResult(activeGameHolder.getActiveGameByClientId(clientId));
                deregisterRequest(clientId);
            }
        }

    }
}
