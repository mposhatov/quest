package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.WarriorRepository;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbHero;
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.holder.ActiveGameSearchRequestHolder;
import com.mposhatov.request.GetNewActiveGameProcessor;
import com.mposhatov.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.request.async.DeferredResult;

@Transactional(noRollbackFor = LogicException.class)
@Controller
public class GameSearchRequestController {

    private final Logger logger = LoggerFactory.getLogger(GameSearchRequestController.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GetNewActiveGameProcessor getNewActiveGameProcessor;

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Autowired
    private ActiveGameSearchRequestHolder activeGameSearchRequestHolder;

    @Autowired
    private WarriorRepository warriorRepository;

    @Value("${game.warriorSlots}")
    private int warriorSlots;

    @RequestMapping(value = "/game-search-request", method = RequestMethod.GET)//todo POST
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    @ResponseBody
    public DeferredResult<ActiveGame> createGameSearchRequest(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientDoesNotExistException, ClientHasActiveGameException, ClientInTheQueueException, HeroDoesNotContainMainWarriors {

        final long clientId = clientSession.getClientId();

        final DbClient client = clientRepository.findOne(clientId);

        if (client == null) {
            throw new ClientDoesNotExistException(clientId);
        }

        final DbHero hero = client.getHero();

        if (warriorRepository.countMainByByHero(hero) <= 0) {
            throw new HeroDoesNotContainMainWarriors(hero.getId());
        }

        if (activeGameSearchRequestHolder.existByClientId(clientId)) {
            throw new ClientInTheQueueException(clientId);
        }

        if (activeGameHolder.existByClientId(clientId)) {
            throw new ClientHasActiveGameException(clientId);
        }

        activeGameSearchRequestHolder.registerGameSearchRequest(
                EntityConverter.toClient(client, true, true, true));

        return getNewActiveGameProcessor.registerRequest(clientId);
    }

    @RequestMapping(value = "/game-search-request", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<Void> deleteGameSearchRequest(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientDoesNotExistException, ClientIsNotInTheQueueException, ClientHasActiveGameException, GetNewActiveGameRequestDoesNotExistException {

        final long clientId = clientSession.getClientId();

        final DbClient client = clientRepository.findOne(clientId);

        if (client == null) {
            throw new ClientDoesNotExistException(clientId);
        }

        if (!activeGameSearchRequestHolder.existByClientId(clientId)) {
            throw new ClientIsNotInTheQueueException(clientId);
        }

        activeGameSearchRequestHolder.deregisterGameSearchRequestByClientId(clientId);

        getNewActiveGameProcessor.deregisterRequest(clientId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
