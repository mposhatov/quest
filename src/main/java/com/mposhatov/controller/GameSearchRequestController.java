package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.GameSearchRequestRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbGameSearchRequest;
import com.mposhatov.exception.ClientDoesNotExistException;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class GameSearchRequestController {

    private final Logger logger = LoggerFactory.getLogger(GameSearchRequestController.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GameSearchRequestRepository gameSearchRequestRepository;

    @RequestMapping(value = "/game-search-request", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<Void> createGameSearchRequest(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientDoesNotExistException {

        final DbClient client = clientRepository.findOne(clientSession.getClientId());

        if (client == null) {
            throw new ClientDoesNotExistException(clientSession.getClientId());
        }

        gameSearchRequestRepository.save(new DbGameSearchRequest(client));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @RequestMapping(value = "/game-search-request", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<Void> deleteGameSearchRequest(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientDoesNotExistException, ClientIsNotInTheQueueException {

        final DbClient client = clientRepository.findOne(clientSession.getClientId());

        if (client == null) {
            throw new ClientDoesNotExistException(clientSession.getClientId());
        }

        final DbGameSearchRequest searchGameRequest = gameSearchRequestRepository.findByClient(client);

        if (searchGameRequest == null) {
            throw new ClientIsNotInTheQueueException(clientSession.getClientId());
        }

        gameSearchRequestRepository.delete(searchGameRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
