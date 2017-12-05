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

    public GetUpdatedActiveGameRequest deregisterGetUpdatedActiveGameRequest(long clientId) throws GetUpdateDActiveGameRequestException.DoesNotExist {

        final GetUpdatedActiveGameRequest getUpdatedActiveGameRequest = requestByClientIds.get(clientId);

        if (getUpdatedActiveGameRequest == null) {
            throw new GetUpdateDActiveGameRequestException.DoesNotExist(clientId);
        }

        return requestByClientIds.remove(clientId);
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
    }

    private StepActiveGame deregisterStepActiveGame(long clientId) throws ClientException.HasNotGameUpdates {

        final Deque<StepActiveGame> stepActiveGames = getStepActiveGamesByClientId(clientId);

        final StepActiveGame stepActiveGame = stepActiveGames.pollFirst();

        if (stepActiveGames.size() == 0) {
            stepActiveGameByClientIds.remove(clientId);
        }

        return stepActiveGame;
    }

    private StepActiveGame getNextStepActiveGameByClientId(long clientId) throws ClientException.HasNotGameUpdates {

        final Deque<StepActiveGame> stepActiveGames = getStepActiveGamesByClientId(clientId);

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

    private Deque<StepActiveGame> getStepActiveGamesByClientId(Long clientId) throws ClientException.HasNotGameUpdates {

        final Deque<StepActiveGame> stepActiveGames = stepActiveGameByClientIds.get(clientId);

        if (stepActiveGames == null || stepActiveGames.isEmpty()) {
            throw new ClientException.HasNotGameUpdates(clientId);
        }

        return stepActiveGames;
    }

    //=========================================================================================

    @Scheduled(fixedDelay = 300)
    public void processRequests() throws LogicException {

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
