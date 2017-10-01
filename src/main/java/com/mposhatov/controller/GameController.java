package com.mposhatov.controller;

import com.mposhatov.ActiveGameHolder;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Command;
import com.mposhatov.exception.*;
import com.mposhatov.service.ActiveGameManager;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Transactional(noRollbackFor = LogicException.class)
public class GameController {

    private final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Autowired
    private ActiveGameManager activeGameManager;

    @RequestMapping(value = "/active-game", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<ActiveGame> getGameSession(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientIsNotInTheQueueException, ActiveGameDoesNotExistException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        return new ResponseEntity<>(activeGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/direct-attack", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<ActiveGame> directAttack(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam(name = "defendingWarriorId", required = true) long defendingWarriorId) throws ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, BlowToAllyException, ClientIsNotInTheQueueException, ActiveGameDoesNotContainCommandsException {

        final long activeGameId = activeGameHolder.getActiveGameIdByClientId(clientSession.getClientId());

        final ActiveGame activeGame = activeGameManager.directAttack(activeGameId, defendingWarriorId);

        return new ResponseEntity<>(activeGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/close", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<ActiveGame> closeGame(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam(name = "activeGameId", required = true) long activeGameId) throws ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, BlowToAllyException, ClientIsNotInTheQueueException, ActiveGameDoesNotContainCommandsException, ClientDoesNotExistException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);

        if (activeGame == null) {
            throw new ActiveGameDoesNotExistException(activeGameId);
        }

        final Command command = activeGame.getCommandByClientId(clientSession.getClientId());

        if (activeGame.isWin(command)) {
            //return closeGame
        } else {
            //throw exception 403
        }

        return new ResponseEntity<>(activeGame, HttpStatus.OK);
    }
}
