package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.WarriorRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbHero;
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.holder.ActiveGameSearchRequest;
import com.mposhatov.holder.ActiveGameSearchRequestHolder;
import com.mposhatov.request.GetUpdatedActiveGameProcessor;
import com.mposhatov.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional(noRollbackFor = LogicException.class)
@Controller
public class GameSearchRequestController {

    private final Logger logger = LoggerFactory.getLogger(GameSearchRequestController.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GetUpdatedActiveGameProcessor getUpdatedActiveGameProcessor;

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Autowired
    private ActiveGameSearchRequestHolder activeGameSearchRequestHolder;

    @Autowired
    private WarriorRepository warriorRepository;

    @ExceptionHandler(LogicException.class)
    @RequestMapping(value = "/game-search-request", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    @ResponseBody
    public ResponseEntity<ActiveGameSearchRequest> createGameSearchRequest(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientDoesNotExistException, ClientHasActiveGameException, ClientInTheQueueException, HeroDoesNotContainMainWarriors {

        final long clientId = clientSession.getClientId();

        final DbClient client = clientRepository.findOne(clientId);

        if (client == null) {
            throw new ClientDoesNotExistException(clientId);
        }

        final DbHero hero = client.getHero();

        if (warriorRepository.countMainByByHero(hero) <= 0) {
            throw new HeroDoesNotContainMainWarriors(hero.getClientId());
        }

        if (activeGameSearchRequestHolder.existByClientId(clientId)) {
            throw new ClientInTheQueueException(clientId);
        }

        if (activeGameHolder.existByClientId(clientId)) {
            throw new ClientHasActiveGameException(clientId);
        }

        final ActiveGameSearchRequest activeGameSearchRequest = activeGameSearchRequestHolder.registerRequest(
                EntityConverter.toClient(client,
                        true,
                        true,
                        true,
                        true));

        return new ResponseEntity<>(activeGameSearchRequest, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/game-search-request", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<Void> deleteGameSearchRequest(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientDoesNotExistException, ClientIsNotInTheQueueException, GetUpdateActiveGameRequestDoesNotExistException {

        final long clientId = clientSession.getClientId();

        final DbClient client = clientRepository.findOne(clientId);

        if (client == null) {
            throw new ClientDoesNotExistException(clientId);
        }

        if (!activeGameSearchRequestHolder.existByClientId(clientId)) {
            throw new ClientIsNotInTheQueueException(clientId);
        }

        activeGameSearchRequestHolder.deregisterRequestByClientId(clientId);

        getUpdatedActiveGameProcessor.deregisterGetUpdatedActiveGameRequest(clientId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
