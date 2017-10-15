package com.mposhatov.controller;

import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Warrior;
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.service.FightSimulator;
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
    private FightSimulator fightSimulator;

    @RequestMapping(value = "/active-game", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<ActiveGame> getGameSession(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientIsNotInTheQueueException, ActiveGameDoesNotExistException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        return new ResponseEntity<>(activeGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/direct-attack", method = RequestMethod.GET)//POST
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<ActiveGame> directAttack(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam(name = "defendingWarriorId", required = true) long defendingWarriorId) throws ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, BlowToAllyException, ClientIsNotInTheQueueException, ActiveGameDoesNotContainCommandsException, ClientHasNotActiveGameException, ExpectedAnotherWarrior {

        final long activeGameId = activeGameHolder.getActiveGameIdByClientId(clientSession.getClientId());

        final ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);

        if (activeGame == null) {
            throw new ActiveGameDoesNotExistException(activeGameId);
        }

        final Warrior attackWarrior = activeGame.getCurrentWarrior();
        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);

        if (!activeGame.getCurrentWarrior().equals(attackWarrior)) {
            throw new ExpectedAnotherWarrior(attackWarrior.getId(), activeGame.getCurrentWarrior().getId());
        }

        if (attackWarrior.getCommand().equals(defendingWarrior.getCommand())) {
            throw new BlowToAllyException(attackWarrior.getId(), defendingWarrior.getId());
        }

        fightSimulator.directionAttack(
                attackWarrior.getWarriorCharacteristics(), defendingWarrior.getWarriorCharacteristics());

        if (defendingWarrior.isDead()) {
            activeGame.registerDeadWarrior(defendingWarrior);
            if (!activeGame.isWin(attackWarrior.getCommand())) {
                activeGame.stepUp();
            }
        }

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

//        if (activeGame.isWin(clientSession.getClientId())) {
//            //return closeGame
//        } else {
//            //throw exception 403
//        }

        return new ResponseEntity<>(activeGame, HttpStatus.OK);
    }
}
