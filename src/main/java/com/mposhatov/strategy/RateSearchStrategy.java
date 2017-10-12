package com.mposhatov.strategy;

import com.mposhatov.ActiveGameSearchRequest;
import com.mposhatov.ActiveGameSearchRequestHolder;
import com.mposhatov.dto.Client;
import com.mposhatov.processor.ClientsOfGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class RateSearchStrategy {

    @Autowired
    private ActiveGameSearchRequestHolder activeGameSearchRequestHolder;

    @Value("${game.options.rateDiff}")
    private int rateDiff;

    @Value("${game.options.requestPackageSize}")
    private int requestPackageSize;

    public List<ClientsOfGame> search() {
        final List<ClientsOfGame> clientsOfGames = new ArrayList<>();

        Client firstCommand = null;
        Client secondCommand = null;

        final Iterator<Map.Entry<Long, ActiveGameSearchRequest>> iterator = activeGameSearchRequestHolder.getIterator();

        while (iterator.hasNext()) {
            final Map.Entry<Long, ActiveGameSearchRequest> entry = iterator.next();

            final ActiveGameSearchRequest activeGameSearchRequest = entry.getValue();

            if (firstCommand == null) {
                firstCommand = activeGameSearchRequest.getClient();
            } else if (secondCommand == null) {
                secondCommand = activeGameSearchRequest.getClient();
            }

            if (firstCommand != null && secondCommand != null) {
                if (Math.abs(firstCommand.getRate() - secondCommand.getRate()) < rateDiff) {
                    clientsOfGames.add(new ClientsOfGame(firstCommand, secondCommand));
                    activeGameSearchRequestHolder.deregisterGameSearchRequest(firstCommand.getId());
                    activeGameSearchRequestHolder.deregisterGameSearchRequest(secondCommand.getId());
                    firstCommand = secondCommand = null;
                }
            }

        }

        return clientsOfGames;
    }
}
