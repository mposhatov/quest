package com.mposhatov.processor;

import com.mposhatov.dao.ActiveRateGameRepository;
import com.mposhatov.dao.AssignRateGameRequestRepository;
import com.mposhatov.entity.DbActiveRateGame;
import com.mposhatov.entity.DbAssignRateGameRequest;
import com.mposhatov.entity.DbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
public class AssignRateGameRequestProcessor {

    @Autowired
    private AssignRateGameRequestRepository assignRateGameRequestRepository;

    @Autowired
    private ActiveRateGameRepository activeRateGameRepository;

    @Value("${rateGame.rateDiff}")
    private int rateDiff;

    @Value("${rateGame.requestPackageSize}")
    private int requestPackageSize;

//    @Scheduled(fixedDelay = 1000)
    public void assign() {
        final Page<DbAssignRateGameRequest> pageRequests = assignRateGameRequestRepository.findAll(
                new PageRequest(0, requestPackageSize, new Sort(Sort.Direction.ASC, "client.rate")));

        final List<DbClient> clients =
                pageRequests.getContent().stream().map(DbAssignRateGameRequest::getClient).collect(Collectors.toList());

        final Map<DbClient, DbAssignRateGameRequest> requestByClients =
                pageRequests.getContent().stream().collect(Collectors.toMap(DbAssignRateGameRequest::getClient, req -> req));

        //todo refactor
        final DbClient[] clientArray = clients.toArray(new DbClient[clients.size()]);
        for (int i = 0; i < clientArray.length - 1; ++i) {
            final DbClient client1 = clientArray[i];
            final DbClient client2 = clientArray[i + 1];
            if (Math.abs(client1.getRate() - client2.getRate()) < rateDiff) {
                activeRateGameRepository.save(new DbActiveRateGame(Arrays.asList(client1, client2)));
                assignRateGameRequestRepository.delete(requestByClients.get(client1));
                assignRateGameRequestRepository.delete(requestByClients.get(client2));
            }
        }
    }
}
