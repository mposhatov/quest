package com.mposhatov.controller;

import com.mposhatov.dao.SpellAttackRepository;
import com.mposhatov.dto.*;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.DbSpellAttack;
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGame;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.request.GetUpdatedActiveGameProcessor;
import com.mposhatov.service.ActiveGameManager;
import com.mposhatov.service.DefendSimulator;
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

    @Autowired
    private DefendSimulator defendSimulator;

    @Autowired
    private SpellAttackRepository spellAttackRepository;

    @ExceptionHandler(LogicException.class)
    @RequestMapping(value = "/active-game", method = RequestMethod.GET)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    @ResponseBody
    public DeferredResult<StepActiveGame> getActiveGame(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) {
        return getUpdatedActiveGameProcessor.registerGetUpdatedActiveGameRequest(clientSession.getClientId());
    }

    @ExceptionHandler(LogicException.class)
    @RequestMapping(value = "/active-game.action/attack/default", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> defaultAttack(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestParam(name = "defendingWarriorId", required = true) Long defendingWarriorId) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, ExpectedAnotherClientException, HitToAllyException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, AttackImpossibilityException, CloseActiveGameException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior attackWarrior = activeGame.getCurrentWarrior();

        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);

        validateActiveGame(attackWarrior, defendingWarrior, clientSession.getClientId());

        if (!activeGameManager.isPossibleStrike(attackWarrior, defendingWarrior, activeGame)) {
            throw new AttackImpossibilityException(attackWarrior.getId(), defendingWarriorId);
        }

        fightSimulator.simpleAttack(attackWarrior, defendingWarrior);

        final boolean gameOver = activeGameManager.refresh(activeGame);

        ClosedGame closedGame = null;

        if (gameOver) {
            closedGame = activeGameManager.closeGame(activeGame.getId());
        } else {
            activeGameManager.stepUp(activeGame);
        }

        final StepActiveGame stepActiveGame =
                activeGameManager.registerStepActiveGame(
                        activeGame,
                        clientSession.getClientId(),
                        attackWarrior.getId(),
                        defendingWarriorId,
                        closedGame);

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/spell/attack", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> spellAttack(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestParam(name = "spellAttackId", required = true) Long spellAttackId,
            @RequestParam(name = "defendingWarriorId", required = true) Long defendingWarriorId) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, ExpectedAnotherClientException, HitToAllyException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, SpellAttackDoesNotExistException, CloseActiveGameException, HeroDoesNotContainSpellAttackException, NotEnoughManaException, WarriorDoesNotContainSpellAttackException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior attackWarrior = activeGame.getCurrentWarrior();
        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);

        validateActiveGame(attackWarrior, defendingWarrior, clientSession.getClientId());

        final DbSpellAttack dbSpellAttack = spellAttackRepository.findOne(spellAttackId);

        if (dbSpellAttack == null) {
            throw new SpellAttackDoesNotExistException(spellAttackId);
        }

        final SpellAttack spellAttack = EntityConverter.toSpellAttack(dbSpellAttack, false, false);

        if (!defendingWarrior.getSpellAttacks().contains(spellAttack)) {
            throw new WarriorDoesNotContainSpellAttackException(clientSession.getClientId(), spellAttackId);
        }

        if (attackWarrior.getWarriorCharacteristics().getMana() < spellAttack.getMana()) {
            throw new NotEnoughManaException(dbSpellAttack.getMana(), attackWarrior.getWarriorCharacteristics().getMana());
        }

        attackWarrior.getWarriorCharacteristics().minusMana(spellAttack.getMana());

        fightSimulator.spellAttack(attackWarrior, spellAttack, defendingWarrior);

        final boolean gameOver = activeGameManager.refresh(activeGame);

        ClosedGame closedGame = null;

        if (gameOver) {
            closedGame = activeGameManager.closeGame(activeGame.getId());
        } else {
            activeGameManager.stepUp(activeGame);
        }

        final StepActiveGame stepActiveGame =
                activeGameManager.registerStepActiveGame(
                        activeGame,
                        clientSession.getClientId(),
                        attackWarrior.getId(),
                        defendingWarriorId,
                        closedGame);

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/defense/default", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> defaultDefense(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ExpectedAnotherClientException, HitToAllyException, ActiveGameDoesNotContainWinClientException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, CloseActiveGameException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior currentWarrior = activeGame.getCurrentWarrior();

        validateActiveGame(currentWarrior, clientSession.getClientId());

        defendSimulator.activateDefaultDefense(currentWarrior);

        final boolean gameOver = activeGameManager.refresh(activeGame);

        ClosedGame closedGame = null;

        if (gameOver) {
            closedGame = activeGameManager.closeGame(activeGame.getId());
        } else {
            activeGameManager.stepUp(activeGame);
        }

        final StepActiveGame stepActiveGame = activeGameManager.registerStepActiveGame(activeGame, clientSession.getClientId(), closedGame);

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/surrendered", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<ClientGameResult> surrendered(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ExpectedAnotherClientException, HitToAllyException, ActiveGameDoesNotContainWinClientException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, CloseActiveGameException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        activeGame.gameOver();
        activeGame.setWinClient(activeGame.getAnotherClient(clientSession.getClientId()));

        final ClosedGame closedGame = activeGameManager.closeGame(activeGame.getId());

        final StepActiveGame stepActiveGame =
                activeGameManager.registerStepActiveGame(activeGame, clientSession.getClientId(), closedGame);

        return new ResponseEntity<>(stepActiveGame.getMyClientGameResult(), HttpStatus.OK);
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
