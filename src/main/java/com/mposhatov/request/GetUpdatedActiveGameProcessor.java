package com.mposhatov.request;

import com.mposhatov.dto.StepActiveGame;
import com.mposhatov.exception.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListMap;

@Service
public class GetUpdatedActiveGameProcessor {

    private Map<Long, GetUpdatedActiveGameRequest> requestByClientIds = new ConcurrentHashMap<>();

    public DeferredResult<StepActiveGame> registerGetUpdatedActiveGameRequest(long clientId) {

        final GetUpdatedActiveGameRequest getUpdatedActiveGameRequest =
                new GetUpdatedActiveGameRequest(clientId);

        requestByClientIds.put(clientId, getUpdatedActiveGameRequest);

        return getUpdatedActiveGameRequest.getDeferredResult();
    }

    public GetUpdatedActiveGameRequest deregisterGetUpdatedActiveGameRequest(long clientId) throws GetUpdateActiveGameRequestDoesNotExistException {

        final GetUpdatedActiveGameRequest request = requestByClientIds.get(clientId);

        if (request == null) {
            throw new GetUpdateActiveGameRequestDoesNotExistException(clientId);
        }

        return requestByClientIds.remove(clientId);
    }

    public boolean existGetUpdatedActiveGameRequestByClientId(long clientId) {
        return requestByClientIds.get(clientId) != null;
    }

    //=========================================================================================

    private Map<Long, Deque<StepActiveGame>> stepActiveGameByClientIds = new ConcurrentSkipListMap<>();

    public void registerStepActiveGame(long clientId, StepActiveGame stepActiveGame) {

        Deque<StepActiveGame> stepActiveGames = stepActiveGameByClientIds.get(clientId);

        if (stepActiveGames == null) {
            stepActiveGames = new ConcurrentLinkedDeque<>();
        }

        stepActiveGames.add(stepActiveGame);
        stepActiveGameByClientIds.put(clientId, stepActiveGames);

        System.out.println();
    }

    private StepActiveGame deregisterStepActiveGame(long clientId) throws ClientHasNotGameUpdatesException {

        final Deque<StepActiveGame> stepActiveGames = stepActiveGameByClientIds.get(clientId);

        if (stepActiveGames == null) {
            throw new ClientHasNotGameUpdatesException(clientId);
        }

        final StepActiveGame stepActiveGame = stepActiveGames.pollFirst();

        if (stepActiveGames.size() == 0) {
            stepActiveGameByClientIds.remove(clientId);
        }

        return stepActiveGame;
    }

    private StepActiveGame getNextStepActiveGameByClientId(long clientId) throws ClientHasNotGameUpdatesException {

        final Deque<StepActiveGame> stepActiveGames = stepActiveGameByClientIds.get(clientId);

        if (stepActiveGames == null) {
            throw new ClientHasNotGameUpdatesException(clientId);
        }

        return stepActiveGames.getFirst();
    }

    private boolean existNextStepActiveGameByClient(long clientId) {

        boolean result = false;

        final Deque<StepActiveGame> stepActiveGames = stepActiveGameByClientIds.get(clientId);

        if (stepActiveGames != null && !stepActiveGames.isEmpty()) {
            result = true;
        }

        return result;
    }

    //=========================================================================================

    @Scheduled(fixedDelay = 300)
    public void processRequests() throws ActiveGameDoesNotExistException, GetUpdateActiveGameRequestDoesNotExistException, InvalidCurrentStepInQueueException, ClientHasNotActiveGameException, ClientHasNotGameUpdatesException {

        for (Map.Entry<Long, GetUpdatedActiveGameRequest> entry : requestByClientIds.entrySet()) {
            final GetUpdatedActiveGameRequest request = entry.getValue();

            final long clientId = request.getClientId();

            if (existNextStepActiveGameByClient(clientId)) {
                request.setResult(getNextStepActiveGameByClientId(clientId));
                deregisterStepActiveGame(clientId);
            }
        }
    }
}
