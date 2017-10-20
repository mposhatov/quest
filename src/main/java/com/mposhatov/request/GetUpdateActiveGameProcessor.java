package com.mposhatov.request;

import com.mposhatov.dto.ActiveGame;
import com.mposhatov.exception.ActiveGameDoesNotExistException;
import com.mposhatov.exception.GetUpdateActiveGameRequestDoesNotExistException;
import com.mposhatov.holder.ActiveGameHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GetUpdateActiveGameProcessor {

    private Map<Long, GetUpdateActiveGameRequest> requestByClientIds = new ConcurrentHashMap<>();

    public DeferredResult<ActiveGame> registerRequest(long clientId, long activeGameId) {

        final GetUpdateActiveGameRequest getUpdateActiveGameRequest =
                new GetUpdateActiveGameRequest(clientId, activeGameId);

        requestByClientIds.put(clientId, getUpdateActiveGameRequest);

        return getUpdateActiveGameRequest.getDeferredResult();
    }

    public GetUpdateActiveGameRequest deregisterRequest(long clientId) throws GetUpdateActiveGameRequestDoesNotExistException {

        final GetUpdateActiveGameRequest request = requestByClientIds.get(clientId);

        if (request == null) {
            throw new GetUpdateActiveGameRequestDoesNotExistException(clientId);
        }

        return requestByClientIds.remove(clientId);
    }

    public boolean existByClientId(long clientId) {
        return requestByClientIds.get(clientId) != null;
    }

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Scheduled(fixedDelay = 100)
    public void processRequests() throws ActiveGameDoesNotExistException, GetUpdateActiveGameRequestDoesNotExistException {

        for (Map.Entry<Long, GetUpdateActiveGameRequest> entry : requestByClientIds.entrySet()) {
            final GetUpdateActiveGameRequest request = entry.getValue();

            final long clientId = request.getClientId();
            final ActiveGame activeGame = activeGameHolder.getActiveGameById(request.getActiveGameId());

            if (activeGame.isUpdatedActiveGameForClient(clientId)) {
                request.setResult(activeGame);
                activeGame.acceptUpdate(clientId);
                deregisterRequest(clientId);
            }
        }

    }


}
