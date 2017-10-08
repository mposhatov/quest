package com.mposhatov.processor;

import com.mposhatov.ActiveGameHolder;
import com.mposhatov.ActiveGameSearchRequestHolder;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.exception.ActiveGameDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UnloadActiveGameProcessor {

    private List<UnloadActiveGameRequest> unloadActiveGameRequests = new CopyOnWriteArrayList<>();

    public void registerUploadActiveGameRequest(UnloadActiveGameRequest unloadActiveGameRequest) {
        this.unloadActiveGameRequests.add(unloadActiveGameRequest);
    }

    public void deregisterUploadActiveGameRequest(UnloadActiveGameRequest unloadActiveGameRequest) {
        this.unloadActiveGameRequests.remove(unloadActiveGameRequest);
    }

    @Autowired
    private ActiveGameSearchRequestHolder activeGameSearchRequestHolder;

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Scheduled(fixedDelay = 1000)
    public void search() throws ActiveGameDoesNotExistException {



        final Iterator<UnloadActiveGameRequest> iterator = unloadActiveGameRequests.iterator();

        while (iterator.hasNext()) {
            final UnloadActiveGameRequest request = iterator.next();
            final ActiveGame activeGame = activeGameHolder.getActiveGameById(request.getActiveGameId());

            if (activeGame == null) {
                final DbActiveGameSearchRequest gameSearchRequest =
                        gameSearchRequestRepository.findByClient(request.getClientId());

                if (gameSearchRequest == null) {
                    iterator.remove();
                }
            } else if (activeGame.isUpdated()) {
                request.setResult(activeGame);
            }

        }
    }

}
