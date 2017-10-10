package com.mposhatov.processor;

import com.mposhatov.ActiveGameHolder;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.exception.ActiveGameDoesNotExistException;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import com.mposhatov.request.GetNewActiveGameRequest;
import com.mposhatov.request.GetUpdateActiveGameRequest;
import com.mposhatov.request.Request;
import com.mposhatov.request.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Iterator;
import java.util.Map;

public class RequestProcessor {

    @Autowired
    private RequestHolder requestHolder;

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Scheduled(fixedDelay = 1000)
    public void search() throws ActiveGameDoesNotExistException, ClientIsNotInTheQueueException {

        final Map<Long, Request> requests = requestHolder.getRequests();

        for (Map.Entry<Long, Request> entry : requests.entrySet()) {
            final long clientId = entry.getKey();
            final Request request = entry.getValue();

            if(request instanceof GetNewActiveGameRequest) {
                final GetNewActiveGameRequest getNewActiveGameRequest = (GetNewActiveGameRequest) request;
                if(activeGameHolder.existByClientId(clientId)) {
                    getNewActiveGameRequest.setResult(activeGameHolder.getActiveGameByClientId(clientId));
                    requestHolder.deregisterGetNewActiveGameRequest(clientId);
                }
            } else if(request instanceof GetUpdateActiveGameRequest) {

            }
        }


        final Iterator<UnloadActiveGameRequest> iterator = unloadActiveGameRequests.iterator();

        while (iterator.hasNext()) {
            final UnloadActiveGameRequest request = iterator.next();
            final ActiveGame activeGame = activeGameHolder.getActiveGameById(request.getActiveGameId());

            if (activeGame == null) {
                final DbActiveGameSearchRequest gameSearchRequest =
                        gameSearchRequestRepository.findByClient(request.getClientId());

                if (gameSearchRequest == null) {
                    iterator.remove();
                }
            } else if (activeGame.isUpdated()) {
                request.setResult(activeGame);
            }

        }
    }

}
