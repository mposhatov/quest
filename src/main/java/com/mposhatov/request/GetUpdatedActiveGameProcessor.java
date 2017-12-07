package com.mposhatov.request;

import com.mposhatov.dto.ClosedGame;
import com.mposhatov.dto.StepActiveGame;
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGame;
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

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame) throws LogicException {
        return registerStepActiveGame(activeGame, null, null, null, null);
    }

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame, long currentClientId) throws LogicException {
        return registerStepActiveGame(activeGame, currentClientId, null, null, null);
    }

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame, long currentClientId, ClosedGame closedGame) throws LogicException {
        return registerStepActiveGame(activeGame, currentClientId, null, null, closedGame);
    }


    public StepActiveGame registerStepActiveGame(ActiveGame activeGame, Long currentClientId, Long attackWarriorId, Long defendingWarriorId) throws LogicException {
        return registerStepActiveGame(activeGame, currentClientId, attackWarriorId, defendingWarriorId, null);
    }

    public StepActiveGame registerStepActiveGame(ActiveGame activeGame, Long currentClientId,
                                                 Long attackWarriorId, Long defendingWarriorId,
                                                 ClosedGame closedGame) throws LogicException {

        final Long firstClientId = activeGame.getFirstClient().getId();
        final Long secondClientId = activeGame.getSecondClient().getId();

        StepActiveGame resultStepActiveGame = null;

        final StepActiveGame stepActiveGameFirstClient =
                buildStepActiveGameForClient(firstClientId, activeGame, attackWarriorId, defendingWarriorId, closedGame);

        final StepActiveGame stepActiveGameSecondClient =
                buildStepActiveGameForClient(secondClientId, activeGame, attackWarriorId, defendingWarriorId, closedGame);

        if (currentClientId != null) {
            if (firstClientId.equals(currentClientId)) {
                registerStepActiveGame(secondClientId, stepActiveGameSecondClient);
                resultStepActiveGame = stepActiveGameFirstClient;
            } else if (secondClientId.equals(currentClientId)) {
                registerStepActiveGame(firstClientId, stepActiveGameFirstClient);
                resultStepActiveGame = stepActiveGameSecondClient;
            }
        } else {
            registerStepActiveGame(firstClientId, stepActiveGameFirstClient);
            registerStepActiveGame(secondClientId, stepActiveGameSecondClient);
            resultStepActiveGame = stepActiveGameFirstClient;
        }

        return resultStepActiveGame;
    }

    private StepActiveGame buildStepActiveGameForClient(Long clientId, ActiveGame activeGame, Long attackWarriorId,
                                                        Long defendingWarriorId, ClosedGame closedGame) throws LogicException {

        final StepActiveGame stepActiveGame =
                new StepActiveGame(activeGame.getQueueWarriors(), activeGame.getCurrentWarrior(), activeGame.isGameOver());

        stepActiveGame.setAttackWarriorId(attackWarriorId);
        stepActiveGame.setDefendWarriorId(defendingWarriorId);

        if (clientId.equals(activeGame.getFirstClient().getId())) {
            stepActiveGame.me(activeGame.getFirstClient());
            stepActiveGame.anotherClient(activeGame.getSecondClient());
        } else if (clientId.equals(activeGame.getSecondClient().getId())) {
            stepActiveGame.me(activeGame.getSecondClient());
            stepActiveGame.anotherClient(activeGame.getFirstClient());
        }

        if (closedGame != null) {
            if (clientId.equals(closedGame.getFirstClientGameResult().getClientId())) {
                stepActiveGame.myClientGameResult(closedGame.getFirstClientGameResult());
            } else if (clientId.equals(closedGame.getSecondClientGameResult().getClientId())) {
                stepActiveGame.myClientGameResult(closedGame.getSecondClientGameResult());
            }
        }

        return stepActiveGame;
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
