package com.mposhatov.processor;

import com.mposhatov.strategy.RateSearchStrategy;
import com.mposhatov.service.ActiveGameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CreateActiveGameProcessor {

    @Autowired
    private RateSearchStrategy rateSearchStrategy;

    @Autowired
    private ActiveGameManager activeGameManager;

//    @Scheduled(fixedDelay = 1000)
    public void create() {
        final List<ClientsOfGame> clientsOfGames = rateSearchStrategy.search();
        clientsOfGames.forEach(clientsOfGame ->
                activeGameManager.createGame(clientsOfGame.getClientFirstCommand(), clientsOfGame.getClientSecondCommand()));
    }
}
