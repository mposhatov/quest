package com.mposhatov.controller;

import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Warrior;
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.request.GetUpdateActiveGameProcessor;
import com.mposhatov.service.ActiveGameManager;
import com.mposhatov.service.FightSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@Transactional(noRollbackFor = LogicException.class)
public class GameController {

    private final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Autowired
    private FightSimulator fightSimulator;

    @Autowired
    private GetUpdateActiveGameProcessor getUpdateActiveGameProcessor;

    @Autowired
    private ActiveGameManager activeGameManager;

    @RequestMapping(value = "/active-game", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    @ResponseBody
    public DeferredResult<ActiveGame> getActiveGame(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        return getUpdateActiveGameProcessor.registerRequest(clientSession.getClientId(), activeGame.getId());
    }

    @RequestMapping(value = "/active-game.action/direct-attack", method = RequestMethod.GET)//POST
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<ActiveGame> directAttack(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam(name = "defendingWarriorId", required = true) long defendingWarriorId) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, ExpectedAnotherWarrior, HitToAllyException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException {

        final long activeGameId = activeGameHolder.getActiveGameIdByClientId(clientSession.getClientId());

        ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);

        final Warrior attackWarrior = activeGame.getCurrentWarrior();
        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);

        if (attackWarrior.getHero().getClient().getId() != clientSession.getClientId()) {
            throw new ExpectedAnotherWarrior(attackWarrior.getId(), activeGame.getCurrentWarrior().getId());
        }

        if (attackWarrior.getHero().getClient().getId() == defendingWarrior.getHero().getClient().getId()) {
            throw new HitToAllyException(attackWarrior.getId(), defendingWarrior.getId());
        }

        fightSimulator.directionAttack(attackWarrior, defendingWarrior);

        boolean gameComplete = false;

        if (defendingWarrior.isDead()) {
            gameComplete = activeGame.registerDeadWarrior(defendingWarrior);
        }

        if (!gameComplete) {
            activeGame.stepUp();
            activeGame.update();
        } else {
            activeGameManager.closeGame(activeGameId);
            activeGame = null;
        }

        return new ResponseEntity<>(activeGame, HttpStatus.OK);
    }
}
