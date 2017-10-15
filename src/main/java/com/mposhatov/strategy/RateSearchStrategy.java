package com.mposhatov.strategy;

import com.mposhatov.exception.LogicException;
import com.mposhatov.holder.ActiveGameSearchRequest;
import com.mposhatov.holder.ActiveGameSearchRequestHolder;
import com.mposhatov.dto.Client;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import com.mposhatov.processor.ClientsOfGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RateSearchStrategy {

    @Value("${game.options.rateDiff}")
    private int rateDiff;

    @Autowired
    private ActiveGameSearchRequestHolder activeGameSearchRequestHolder;

    public List<ClientsOfGame> search() throws ClientIsNotInTheQueueException {
        final List<ClientsOfGame> clientsOfGames = new ArrayList<>();

        final List<ActiveGameSearchRequest> requests = activeGameSearchRequestHolder.getRequests();

        requests.sort((reg1, reg2) -> {
            int rateDiff = (int) (reg1.getClient().getRate() - reg2.getClient().getRate());
            int dateDiff = reg1.getCreatedAt().compareTo(reg2.getCreatedAt());
            return rateDiff == 0 ? dateDiff : rateDiff;
        });

        Client firstCommand = null;
        Client secondCommand = null;

        for (ActiveGameSearchRequest request : requests) {
            if (firstCommand == null) {
                firstCommand = request.getClient();
            } else {
                secondCommand = request.getClient();
            }

            if (firstCommand != null && secondCommand != null) {
                if (Math.abs(firstCommand.getRate() - secondCommand.getRate()) < rateDiff) {
                    if (activeGameSearchRequestHolder.existByClientId(firstCommand.getId())
                            && activeGameSearchRequestHolder.existByClientId(secondCommand.getId())) {

                        clientsOfGames.add(new ClientsOfGame(firstCommand, secondCommand));

                        activeGameSearchRequestHolder.deregisterGameSearchRequestByClientId(firstCommand.getId());
                        activeGameSearchRequestHolder.deregisterGameSearchRequestByClientId(secondCommand.getId());

                        firstCommand = secondCommand = null;
                    }
                } else {
                    firstCommand = secondCommand;
                    secondCommand = null;
                }
            }
        }
        return clientsOfGames;
    }
}

