package com.mposhatov.strategy;

import com.mposhatov.dto.Client;
import com.mposhatov.exception.ClientException;
import com.mposhatov.holder.ActiveGameSearchRequest;
import com.mposhatov.holder.ActiveGameSearchRequestHolder;
import com.mposhatov.processor.ClientsOfGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingSearchStrategy {

    private final Logger logger = LoggerFactory.getLogger(RatingSearchStrategy.class);

    @Value("${game.options.ratingDiff}")
    private int ratingDiff;

    @Autowired
    private ActiveGameSearchRequestHolder activeGameSearchRequestHolder;

    public List<ClientsOfGame> search() {

        final List<ClientsOfGame> clientsOfGames = new ArrayList<>();

        final List<ActiveGameSearchRequest> requests = activeGameSearchRequestHolder.getRequests();

        requests.sort((reg1, reg2) -> {
            int ratingDiff = (int) (reg1.getClient().getRating() - reg2.getClient().getRating());
            int dateDiff = reg1.getCreatedAt().compareTo(reg2.getCreatedAt());
            return ratingDiff == 0 ? dateDiff : ratingDiff;
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
                if (Math.abs(firstCommand.getRating() - secondCommand.getRating()) <= ratingDiff) {
                    if (activeGameSearchRequestHolder.existByClientId(firstCommand.getId())
                            && activeGameSearchRequestHolder.existByClientId(secondCommand.getId())) {

                        clientsOfGames.add(new ClientsOfGame(firstCommand, secondCommand));

                        try {
                            activeGameSearchRequestHolder.deregisterRequestByClientId(firstCommand.getId());
                            activeGameSearchRequestHolder.deregisterRequestByClientId(secondCommand.getId());
                        } catch (ClientException.IsNotInTheQueue isNotInTheQueue) {
                            logger.error(isNotInTheQueue.getMessage(), isNotInTheQueue);
                        }

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

