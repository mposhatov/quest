package com.mposhatov.strategy;

import com.mposhatov.dao.SearchGameRequestRepository;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbSearchGameRequest;
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
    private SearchGameRequestRepository searchGameRequestRepository;

    @Value("${rateGame.rateDiff}")
    private int rateDiff;

    @Value("${rateGame.requestPackageSize}")
    private int requestPackageSize;

    public List<ClientsOfGame> search() {
//                final Set<ClientsOfGame> search = searchGameRequestRepository.search(rateDiff);

        final List<ClientsOfGame> clientsOfGames = new ArrayList<>();

        final Page<DbSearchGameRequest> pageRequests = searchGameRequestRepository.findAll(
                new PageRequest(0, requestPackageSize, new Sort(Sort.Direction.ASC, "client.rate")));

        final Map<DbClient, DbSearchGameRequest> requestByClients =
                pageRequests.getContent().stream().collect(Collectors.toMap(DbSearchGameRequest::getClient, req -> req));

        final ArrayList<DbClient> dbClients = new ArrayList<>(requestByClients.keySet());

        for (int i = 1; i < dbClients.size(); ++i) {
            final DbClient dbClient1 = dbClients.get(i - 1);
            final DbClient dbClient2 = dbClients.get(i);

            if (Math.abs(dbClient1.getRate() - dbClient2.getRate()) < rateDiff) {
                searchGameRequestRepository.delete(requestByClients.get(dbClient1));
                searchGameRequestRepository.delete(requestByClients.get(dbClient2));
                searchGameRequestRepository.flush();
                clientsOfGames.add(new ClientsOfGame(dbClient1, dbClient2));
                i++;
            }
        }
        return clientsOfGames;
    }

}
