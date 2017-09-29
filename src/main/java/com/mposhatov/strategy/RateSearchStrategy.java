package com.mposhatov.strategy;

import com.mposhatov.dao.GameSearchRequestRepository;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbGameSearchRequest;
import com.mposhatov.processor.ClientsOfGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RateSearchStrategy {

    @Autowired
    private GameSearchRequestRepository gameSearchRequestRepository;

    @Value("${game.options.rateDiff}")
    private int rateDiff;

    @Value("${game.options.requestPackageSize}")
    private int requestPackageSize;

    public List<ClientsOfGame> search() {

        final List<ClientsOfGame> clientsOfGames = new ArrayList<>();

        final Page<DbGameSearchRequest> pageRequests = gameSearchRequestRepository.findAll(
                new PageRequest(0, requestPackageSize, new Sort(Sort.Direction.ASC, "client.rate")));

        final Map<DbClient, DbGameSearchRequest> requestByClients =
                pageRequests.getContent().stream().collect(Collectors.toMap(DbGameSearchRequest::getClient, req -> req));

        final ArrayList<DbClient> dbClients = new ArrayList<>(requestByClients.keySet());

        for (int i = 1; i < dbClients.size(); ++i) {
            final DbClient dbClientFirstCommand = dbClients.get(i - 1);
            final DbClient dbClientSecondCommand = dbClients.get(i);

            if (Math.abs(dbClientFirstCommand.getRate() - dbClientSecondCommand.getRate()) < rateDiff) {
                gameSearchRequestRepository.delete(requestByClients.get(dbClientFirstCommand));
                gameSearchRequestRepository.delete(requestByClients.get(dbClientSecondCommand));
                clientsOfGames.add(new ClientsOfGame(dbClientFirstCommand, dbClientSecondCommand));
                i++;
            }
        }
        return clientsOfGames;
    }

}
