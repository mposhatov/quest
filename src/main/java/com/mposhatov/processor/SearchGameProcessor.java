package com.mposhatov.processor;

import com.mposhatov.strategy.RateSearchStrategy;
import com.mposhatov.service.GameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SearchGameProcessor {

    @Autowired
    private RateSearchStrategy rateSearchStrategy;

    @Autowired
    private GameManager gameManager;

    @Scheduled(fixedDelay = 1000)
    public void create() {
        final List<ClientsOfGame> clientsOfGames = rateSearchStrategy.search();
        clientsOfGames.forEach(clientsOfGame ->
                gameManager.createGame(clientsOfGame.getClient1(), clientsOfGame.getClient2()));

    }
}
