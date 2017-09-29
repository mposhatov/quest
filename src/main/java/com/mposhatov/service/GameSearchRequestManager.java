package com.mposhatov.service;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.GameSearchRequestRepository;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbGameSearchRequest;
import com.mposhatov.exception.ClientDoesNotExistException;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameSearchRequestManager {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GameSearchRequestRepository gameSearchRequestRepository;

    /**
     * Create gameSearchRequest for a giving client.(In Other words add a client
     * in the game search queue.)
     *
     * @param clientId client.id
     * @return GameSearchRequest
     * @throws ClientDoesNotExistException if a client with a giving id does not exist
     */
    public DbGameSearchRequest createGameSearchRequest(long clientId) throws ClientDoesNotExistException {

        final DbClient client = clientRepository.findOne(clientId);

        if (client == null) {
            throw new ClientDoesNotExistException(clientId);
        }

        return gameSearchRequestRepository.save(new DbGameSearchRequest(client));

    }

    /**
     * Delete gameSearchRequest for a giving client.(In Other words remove a client
     * of the game search queue.)
     *
     * @param clientId client.id
     * @throws ClientDoesNotExistException    if a client with a giving id does not exist
     * @throws ClientIsNotInTheQueueException if a client does not have the gameSearchRequest.
     *                                        (In other words client does not stand in game search queue.)
     */
    public void deleteGameSearchRequest(long clientId) throws ClientDoesNotExistException, ClientIsNotInTheQueueException {

        final DbClient client = clientRepository.findOne(clientId);

        if (client == null) {
            throw new ClientDoesNotExistException(clientId);
        }

        final DbGameSearchRequest searchGameRequest = gameSearchRequestRepository.findByClient(client);

        if (searchGameRequest == null) {
            throw new ClientIsNotInTheQueueException(clientId);
        }

        gameSearchRequestRepository.delete(searchGameRequest);

    }
}
