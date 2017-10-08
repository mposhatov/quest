package com.mposhatov.controller;

import com.mposhatov.ActiveGameHolder;
import com.mposhatov.ActiveGameSearchRequestHolder;
import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dto.ActiveGameSearchRequest;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbClient;
import com.mposhatov.exception.ClientDoesNotExistException;
import com.mposhatov.exception.ClientHasActiveGameException;
import com.mposhatov.exception.ClientInTheQueueException;
import com.mposhatov.exception.ClientIsNotInTheQueueException;
import com.mposhatov.processor.UnloadActiveGameProcessor;
import com.mposhatov.processor.UnloadActiveGameRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class GameSearchRequestController {

    private final Logger logger = LoggerFactory.getLogger(GameSearchRequestController.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ActiveGameSearchRequestHolder activeGameSearchRequestHolder;

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Autowired
    private UnloadActiveGameProcessor unloadActiveGameProcessor;

    @Autowired

    @RequestMapping(value = "/game-search-request", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public @ResponseBody UnloadActiveGameRequest createGameSearchRequest(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientDoesNotExistException, ClientHasActiveGameException, ClientInTheQueueException {

        final DbClient client = clientRepository.findOne(clientSession.getClientId());

        if (client == null) {
            throw new ClientDoesNotExistException(clientSession.getClientId());
        }

        if (activeGameHolder.existByClientId(clientSession.getClientId())) {
            throw new ClientHasActiveGameException(clientSession.getClientId());
        }

        if (activeGameSearchRequestHolder.existByClientId(clientSession.getClientId())) {
            throw new ClientInTheQueueException(clientSession.getClientId());
        }

        activeGameSearchRequestHolder.registerGameSearchRequest(new ActiveGameSearchRequest(clientSession.getClientId()));

        final UnloadActiveGameRequest unloadActiveGameRequest =
                new UnloadActiveGameRequest().setClientId(clientSession.getClientId());

        unloadActiveGameProcessor.registerUploadActiveGameRequest(unloadActiveGameRequest);

        return unloadActiveGameRequest;

    }

    @RequestMapping(value = "/game-search-request", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<Void> deleteGameSearchRequest(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientDoesNotExistException, ClientIsNotInTheQueueException, ClientHasActiveGameException {

        final DbClient client = clientRepository.findOne(clientSession.getClientId());

        if (client == null) {
            throw new ClientDoesNotExistException(clientSession.getClientId());
        }

        if (!activeGameSearchRequestHolder.existByClientId(clientSession.getClientId())) {
            if (activeGameHolder.existByClientId(clientSession.getClientId())) {
                throw new ClientHasActiveGameException(clientSession.getClientId());
            } else {
                throw new ClientIsNotInTheQueueException(clientSession.getClientId());
            }
        }

        activeGameSearchRequestHolder.deregisterGameSearchRequest(clientSession.getClientId());

        unloadActiveGameProcessor.deregisterUploadActiveGameRequest(clientSession.getClientId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
