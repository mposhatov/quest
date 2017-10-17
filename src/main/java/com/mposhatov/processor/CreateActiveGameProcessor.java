package com.mposhatov.processor;

import com.mposhatov.exception.ClientIsNotInTheQueueException;
import com.mposhatov.exception.LogicException;
import com.mposhatov.service.ActiveGameManager;
import com.mposhatov.strategy.RatingSearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(noRollbackFor = LogicException.class)
@Service
public class CreateActiveGameProcessor {

    @Autowired
    private RatingSearchStrategy ratingSearchStrategy;

    @Autowired
    private ActiveGameManager activeGameManager;

    @Scheduled(fixedDelay = 1000)
    public void create() throws ClientIsNotInTheQueueException {

        final List<ClientsOfGame> clientsOfGames = ratingSearchStrategy.search();

        for (ClientsOfGame clientsOfGame : clientsOfGames) {
            activeGameManager.createGame(clientsOfGame.getFirstCommand(), clientsOfGame.getSecondCommand());
        }
    }
}
