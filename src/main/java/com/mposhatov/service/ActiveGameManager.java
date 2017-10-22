package com.mposhatov.service;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.ClosedGameRepository;
import com.mposhatov.holder.ActiveGame;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbClientGameResult;
import com.mposhatov.entity.DbClosedGame;
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.request.GetUpdatedActiveGameProcessor;
import com.mposhatov.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ActiveGameManager {

    private final Logger logger = LoggerFactory.getLogger(ActiveGameManager.class);

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Autowired
    private GetUpdatedActiveGameProcessor getUpdatedActiveGameProcessor;

    @Autowired
    private ClosedGameRepository closedGameRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ActiveGame createGame(Client firstClient, Client secondClient) throws ClientIsNotInTheQueueException {

        final List<Warrior> queueWarriors = new ArrayList<>();

        queueWarriors.addAll(firstClient.getHero().getWarriors());
        queueWarriors.addAll(secondClient.getHero().getWarriors());

        queueWarriors.sort(Comparator.comparing(o -> o.getWarriorCharacteristics().getVelocity()));

        final ActiveGame activeGame = new ActiveGame(
                activeGameHolder.generateActiveGameId(), firstClient, secondClient, queueWarriors).update();

        activeGameHolder.registerActiveGame(activeGame);

        return activeGame;
    }

    public DbClosedGame closeGame(long activeGameId) throws ActiveGameDoesNotExistException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, InvalidCurrentStepInQueueException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);
        final Client winClient = activeGame.getWinClient();

        if (winClient == null) {
            throw new ActiveGameDoesNotContainWinClientException(activeGame.getId());
        }

        final Client firstClient = activeGame.getClients().get(0);
        final Client secondClient = activeGame.getClients().get(1);

        if (firstClient == null || secondClient == null || activeGame.getClients().size() != 2) {
            throw new ActiveGameDoesNotContainTwoClientsException(activeGame.getId());
        }

        for (Client client : activeGame.getClients()) {
            if (getUpdatedActiveGameProcessor.existByClientId(client.getId())) {
                getUpdatedActiveGameProcessor.deregisterRequest(client.getId())
                        .setResult(EntityConverter.toActiveGame(activeGame));
            }
        }

        DbClosedGame closedGame = closedGameRepository.save(new DbClosedGame(activeGame.getCreateAt()));

        final DbClient dbFirstClient = clientRepository.getOne(firstClient.getId());
        final DbClient dbSecondClient = clientRepository.getOne(secondClient.getId());

        final boolean firstClientWin = winClient.getId() == dbFirstClient.getId();
        final boolean secondClientWin = winClient.getId() == dbSecondClient.getId();

        final long firstClientAddRating = firstClientWin ? 5 : -5;
        final long secondClientAddRation = secondClientWin ? 5 : -5;

        dbFirstClient.addRating(firstClientAddRating);
        dbSecondClient.addRating(secondClientAddRation);

        DbClientGameResult firstClientGameResult =
                new DbClientGameResult(dbFirstClient, closedGame, firstClientWin, firstClientAddRating);

        DbClientGameResult secondClientGameResult =
                new DbClientGameResult(dbSecondClient, closedGame, secondClientWin, secondClientAddRation);

        closedGame.addGameResults(Arrays.asList(firstClientGameResult, secondClientGameResult));

        activeGameHolder.deregisterActiveGame(activeGame.getId());

        closedGameRepository.flush();

        return closedGame;
    }
}