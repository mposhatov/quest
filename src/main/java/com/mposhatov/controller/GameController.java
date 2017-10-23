package com.mposhatov.controller;

import com.mposhatov.dto.Client;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.StepActiveGame;
import com.mposhatov.dto.Warrior;
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGame;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.request.GetUpdatedActiveGameProcessor;
import com.mposhatov.service.ActiveGameManager;
import com.mposhatov.service.FightSimulator;
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
    private GetUpdatedActiveGameProcessor getUpdatedActiveGameProcessor;

    @Autowired
    private ActiveGameManager activeGameManager;

    @RequestMapping(value = "/active-game", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    @ResponseBody
    public DeferredResult<StepActiveGame> getActiveGame(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {

        return getUpdatedActiveGameProcessor.registerGetUpdatedActiveGameRequest(clientSession.getClientId());
    }

    @RequestMapping(value = "/active-game.action/direct-attack", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<StepActiveGame> directAttack(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam(name = "defendingWarriorId", required = true) long defendingWarriorId) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, ExpectedAnotherWarrior, HitToAllyException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException {

        ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

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

        if (gameComplete) {
            activeGameManager.closeGame(activeGame.getId());
        } else {
            activeGame.stepUp();
        }

        final StepActiveGame stepActiveGame = EntityConverter.toStepActiveGame(activeGame)
                .setAttackWarriorId(attackWarrior.getId())
                .setDefendWarriorId(defendingWarrior.getId());

        for (Client client : activeGame.getClients()) {
            getUpdatedActiveGameProcessor.registerStepActiveGame(client.getId(), stepActiveGame);
        }

        return new ResponseEntity<>(EntityConverter.toStepActiveGame(activeGame), HttpStatus.OK);
    }
}
