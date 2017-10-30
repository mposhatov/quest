package com.mposhatov.processor;

import com.mposhatov.exception.*;
import com.mposhatov.service.ActiveGameManager;
import com.mposhatov.strategy.RatingSearchStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(noRollbackFor = LogicException.class)
@Service
public class CreateActiveGameProcessor {

    private final Logger logger = LoggerFactory.getLogger(CreateActiveGameProcessor.class);

    @Autowired
    private RatingSearchStrategy ratingSearchStrategy;

    @Autowired
    private ActiveGameManager activeGameManager;

    @Scheduled(fixedDelay = 1000)
    public void create() throws ClientIsNotInTheQueueException, InvalidCurrentStepInQueueException, ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException {

        final List<ClientsOfGame> clientsOfGames = ratingSearchStrategy.search();

        for (ClientsOfGame clientsOfGame : clientsOfGames) {
            activeGameManager.createGame(clientsOfGame.getFirstClient(), clientsOfGame.getSecondClient());
        }
    }
}
