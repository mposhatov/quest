package com.mposhatov.request;

import com.mposhatov.exception.ClientHasNotActiveGameException;
import com.mposhatov.exception.InvalidCurrentStepInQueueException;
import com.mposhatov.holder.ActiveGame;
import com.mposhatov.exception.ActiveGameDoesNotExistException;
import com.mposhatov.exception.GetUpdateActiveGameRequestDoesNotExistException;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GetUpdatedActiveGameProcessor {

    private Map<Long, GetUpdateActiveGameRequest> requestByClientIds = new ConcurrentHashMap<>();

    public DeferredResult<com.mposhatov.dto.ActiveGame> registerRequest(long clientId) {

        final GetUpdateActiveGameRequest getUpdateActiveGameRequest =
                new GetUpdateActiveGameRequest(clientId);

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

    @Scheduled(fixedDelay = 1000)
    public void processRequests() throws ActiveGameDoesNotExistException, GetUpdateActiveGameRequestDoesNotExistException, InvalidCurrentStepInQueueException, ClientHasNotActiveGameException {

        for (Map.Entry<Long, GetUpdateActiveGameRequest> entry : requestByClientIds.entrySet()) {
            final GetUpdateActiveGameRequest request = entry.getValue();

            final long clientId = request.getClientId();

            if (activeGameHolder.existByClientId(clientId)) {
                final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientId);

                if (activeGame.isUpdatedActiveGameForClient(clientId)) {
                    request.setResult(EntityConverter.toActiveGame(activeGame));
                    activeGame.acceptUpdate(clientId);
                    deregisterRequest(clientId);
                }
            }
        }

    }


}
