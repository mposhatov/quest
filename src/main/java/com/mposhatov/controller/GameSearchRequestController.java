package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.WarriorRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbHero;
import com.mposhatov.exception.ClientException;
import com.mposhatov.exception.HeroException;
import com.mposhatov.exception.LogicException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

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

    @RequestMapping(value = "/game-search-request", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<ActiveGameSearchRequest> createGameSearchRequest(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) throws LogicException {

        final DbClient client = getClient(clientSession.getClientId());

        final DbHero hero = client.getHero();

        if (warriorRepository.countMainByByHero(hero) <= 0) {
            throw new HeroException.DoesNotContainMainWarriors(hero.getClientId());
        }

        if (activeGameSearchRequestHolder.existByClientId(clientSession.getClientId())) {
            throw new ClientException.InTheQueue(clientSession.getClientId());
        }

        if (activeGameHolder.existByClientId(clientSession.getClientId())) {
            throw new ClientException.HasActiveGame(clientSession.getClientId());
        }

        final ActiveGameSearchRequest activeGameSearchRequest = activeGameSearchRequestHolder.registerRequest(
                EntityConverter.toClient(client,
                        true,
                        true,
                        true,
                        true,
                        true,
                        true,
                        true,
                        true));

        return new ResponseEntity<>(activeGameSearchRequest, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/game-search-request", method = RequestMethod.DELETE)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<Void> deleteGameSearchRequest(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) throws LogicException {

        getClient(clientSession.getClientId());

        if (!activeGameSearchRequestHolder.existByClientId(clientSession.getClientId())) {
            throw new ClientException.IsNotInTheQueue(clientSession.getClientId());
        }

        activeGameSearchRequestHolder.deregisterRequestByClientId(clientSession.getClientId());

        getUpdatedActiveGameProcessor.deregisterGetUpdatedActiveGameRequest(clientSession.getClientId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private DbClient getClient(Long clientId) throws ClientException.DoesNotExist {

        final DbClient client = clientRepository.findOne(clientId);

        if (client == null) {
            throw new ClientException.DoesNotExist(clientId);
        }

        return client;
    }

}
