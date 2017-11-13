package com.mposhatov.controller;

import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.StepActiveGame;
import com.mposhatov.dto.Warrior;
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGame;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.request.GetUpdatedActiveGameProcessor;
import com.mposhatov.service.ActiveGameManager;
import com.mposhatov.service.DefendSimulator;
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
    private GetUpdatedActiveGameProcessor getUpdatedActiveGameProcessor;

    @Autowired
    private ActiveGameManager activeGameManager;

    @Autowired
    private DefendSimulator defendSimulator;

    @RequestMapping(value = "/active-game", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    @ResponseBody
    public DeferredResult<StepActiveGame> getActiveGame(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {
        return getUpdatedActiveGameProcessor.registerGetUpdatedActiveGameRequest(clientSession.getClientId());
    }

    @ExceptionHandler(LogicException.class)
    @RequestMapping(value = "/active-game.action/attack/default", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<StepActiveGame> defaultAttack(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam(name = "defendingWarriorId", required = true) Long defendingWarriorId) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, ExpectedAnotherClientException, HitToAllyException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior attackWarrior = activeGame.getCurrentWarrior();
        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);

        validateActiveGame(attackWarrior, defendingWarrior, clientSession.getClientId());

        fightSimulator.simpleAttack(attackWarrior, defendingWarrior);

        final StepActiveGame stepActiveGame =
                activeGameManager.registerStepActiveGame(activeGame, attackWarrior.getId(), defendingWarriorId, clientSession.getClientId());

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

//    @RequestMapping(value = "/active-game.action/attack/spell", method = RequestMethod.POST)
//    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
//    public ResponseEntity<StepActiveGame> magicAttack(
//            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
//            @RequestParam(name = "spellId", required = true) Long spellId,
//            @RequestParam(name = "defendingWarriorId", required = true) Long defendingWarriorId) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, ExpectedAnotherClientException, HitToAllyException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException {
//
//        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());
//
//        final Warrior attackWarrior = activeGame.getCurrentWarrior();
//        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);
//
//        validateActiveGame(attackWarrior, defendingWarrior, clientSession.getClientId());
//
////        fightSimulator.magicAttack(attackWarrior, defendingWarrior);
//
//        final StepActiveGame stepActiveGame = activeGameManager.registerStepActiveGame(activeGame, defendingWarriorId);
//
//        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
//    }

    @RequestMapping(value = "/active-game.action/defense/default", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<StepActiveGame> defaultDefense(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ExpectedAnotherClientException, HitToAllyException, ActiveGameDoesNotContainWinClientException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior currentWarrior = activeGame.getCurrentWarrior();

        validateActiveGame(currentWarrior, clientSession.getClientId());

        defendSimulator.activateDefaultDefense(currentWarrior);

        final StepActiveGame stepActiveGame = activeGameManager.registerStepActiveGame(activeGame, clientSession.getClientId());

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/surrendered", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<StepActiveGame> surrendered(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ExpectedAnotherClientException, HitToAllyException, ActiveGameDoesNotContainWinClientException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        activeGame.setWinClient(activeGame.getFirstClient().getId() == clientSession.getClientId() ?
                activeGame.getSecondClient().getId() : activeGame.getFirstClient().getId());

        final StepActiveGame stepActiveGame = activeGameManager.registerStepClosingActiveGame(activeGame);

        activeGameManager.closeGame(activeGame.getId());

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    private void validateActiveGame(Warrior attackWarrior, Long clientId) throws ExpectedAnotherClientException, HitToAllyException {

        if (attackWarrior.getHero().getClient().getId() != clientId) {
            throw new ExpectedAnotherClientException(clientId);
        }

    }

    private void validateActiveGame(Warrior attackWarrior, Warrior defendingWarrior, Long clientId) throws ExpectedAnotherClientException, HitToAllyException {

        validateActiveGame(attackWarrior, clientId);

        if (attackWarrior.getHero().getClient().getId() == defendingWarrior.getHero().getClient().getId()) {
            throw new HitToAllyException(attackWarrior.getId(), defendingWarrior.getId());
        }

    }

}
