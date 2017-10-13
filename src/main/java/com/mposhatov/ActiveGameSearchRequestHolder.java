package com.mposhatov;

import com.mposhatov.dto.Client;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
public class ActiveGameSearchRequestHolder {

    private Set<ActiveGameSearchRequest> activeGameSearchRequests =
            new ConcurrentSkipListSet<>((r1, r2) -> (int) (r1.getClient().getRate() - r2.getClient().getRate()));

    public void registerGameSearchRequest(Client client) {
        this.activeGameSearchRequests.add(new ActiveGameSearchRequest(client));
    }

    public void deregisterGameSearchRequestByClientId(long clientId) throws ClientIsNotInTheQueueException {

        final ActiveGameSearchRequest activeGameSearchRequest = findByClientId(clientId);

        if (activeGameSearchRequest == null) {
            throw new ClientIsNotInTheQueueException(clientId);
        }

        this.activeGameSearchRequests.remove(activeGameSearchRequest);
    }

    private ActiveGameSearchRequest getByClientId(long clientId) throws ClientIsNotInTheQueueException {

        final ActiveGameSearchRequest activeGameSearchRequest = findByClientId(clientId);

        if (activeGameSearchRequest == null) {
            throw new ClientIsNotInTheQueueException(clientId);
        }

        return activeGameSearchRequest;
    }

    public Iterator<ActiveGameSearchRequest> getIterator() {
        return activeGameSearchRequests.iterator();
    }

    public boolean existByClientId(Long clientId) {
        return findByClientId(clientId) != null;
    }

    private ActiveGameSearchRequest findByClientId(long clientId) {
        return this.activeGameSearchRequests.stream()
                .filter(r -> r.getClient().getId() == clientId)
                .findFirst()
                .orElse(null);
    }

}

