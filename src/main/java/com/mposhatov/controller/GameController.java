package com.mposhatov.controller;

import com.mposhatov.dao.SpellAttackRepository;
import com.mposhatov.dao.SpellExhortationRepository;
import com.mposhatov.dao.SpellHealRepository;
import com.mposhatov.dao.SpellPassiveRepository;
import com.mposhatov.dto.*;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.DbSpellAttack;
import com.mposhatov.entity.DbSpellExhortation;
import com.mposhatov.entity.DbSpellHeal;
import com.mposhatov.entity.DbSpellPassive;
import com.mposhatov.exception.LogicException;
import com.mposhatov.exception.SpellException;
import com.mposhatov.holder.ActiveGame;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.request.GetUpdatedActiveGameProcessor;
import com.mposhatov.service.ActiveGameManager;
import com.mposhatov.service.validator.FightExceptionThrower;
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
    private GetUpdatedActiveGameProcessor getUpdatedActiveGameProcessor;

    @Autowired
    private ActiveGameManager activeGameManager;

    @Autowired
    private SpellAttackRepository spellAttackRepository;

    @Autowired
    private SpellHealRepository spellHealRepository;

    @Autowired
    private SpellExhortationRepository spellExhortationRepository;

    @Autowired
    private SpellPassiveRepository spellPassiveRepository;

    @Autowired
    private FightExceptionThrower fightExceptionThrower;

    @RequestMapping(value = "/active-game", method = RequestMethod.GET)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    @ResponseBody
    public DeferredResult<StepActiveGame> getActiveGame(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) {
        return getUpdatedActiveGameProcessor.registerGetUpdatedActiveGameRequest(clientSession.getClientId());
    }

    @RequestMapping(value = "/active-game.action/attack/default", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> defaultAttack(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestParam(name = "defendingWarriorId", required = true) Long defendingWarriorId) throws LogicException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior attackWarrior = activeGame.getCurrentWarrior();

        fightExceptionThrower.throwIfExpectedAnotherClient(attackWarrior, clientSession.getClientId());

        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);

        fightExceptionThrower.throwIfWrongTarget(activeGame, attackWarrior.getWarriorCharacteristics().getTarget(), attackWarrior, defendingWarrior);

        activeGameManager.simpleAttack(attackWarrior, defendingWarrior);

        ClosedGame closedGame = null;

        final boolean gameOver = activeGameManager.refresh(activeGame);

        if (gameOver) {
            closedGame = activeGameManager.closeGame(activeGame.getId());
        } else {
            activeGameManager.stepUp(activeGame);
        }

        final StepActiveGame stepActiveGame =
                getUpdatedActiveGameProcessor.registerStepActiveGame(
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
            @RequestParam(name = "defendingWarriorId", required = true) Long defendingWarriorId) throws LogicException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior attackWarrior = activeGame.getCurrentWarrior();

        fightExceptionThrower.throwIfExpectedAnotherClient(attackWarrior, clientSession.getClientId());

        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);

        final DbSpellAttack dbSpellAttack = getSpellAttack(spellAttackId);

        final SpellAttack spellAttack = EntityConverter.toSpellAttack(dbSpellAttack, false, false);

        fightExceptionThrower.throwIfWarriorDoesNotContainSpellAttack(attackWarrior, spellAttack);
        fightExceptionThrower.throwIfWrongTarget(activeGame, spellAttack.getTarget(), attackWarrior, defendingWarrior);
        fightExceptionThrower.throwIfNotEnoughMana(attackWarrior, spellAttack.getMana());

        activeGameManager.spellAttack(attackWarrior, spellAttack, defendingWarrior);

        ClosedGame closedGame = null;

        final boolean gameOver = activeGameManager.refresh(activeGame);

        if (gameOver) {
            closedGame = activeGameManager.closeGame(activeGame.getId());
        } else {
            activeGameManager.stepUp(activeGame);
        }

        final StepActiveGame stepActiveGame =
                getUpdatedActiveGameProcessor.registerStepActiveGame(
                        activeGame,
                        clientSession.getClientId(),
                        attackWarrior.getId(),
                        defendingWarriorId,
                        closedGame);

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/spell/heal", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> spellHeal(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestParam(name = "spellHealId", required = true) Long spellHealId,
            @RequestParam(name = "warriorId", required = true) Long warriorId) throws LogicException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior castingWarrior = activeGame.getCurrentWarrior();

        fightExceptionThrower.throwIfExpectedAnotherClient(castingWarrior, clientSession.getClientId());

        final Warrior targetWarrior = activeGame.getWarriorById(warriorId);

        final DbSpellHeal dbSpellHeal = getSpellHeal(spellHealId);

        final SpellHeal spellHeal = EntityConverter.toSpellHeal(dbSpellHeal, false, false);

        fightExceptionThrower.throwIfWarriorDoesNotContainSpellHeal(castingWarrior, spellHeal);
        fightExceptionThrower.throwIfNotEnoughMana(castingWarrior, spellHeal.getMana());
        fightExceptionThrower.throwIfWrongTarget(activeGame, spellHeal.getTarget(), castingWarrior, targetWarrior);

        activeGameManager.spellHeal(castingWarrior, spellHeal, targetWarrior);

        ClosedGame closedGame = null;

        final boolean gameOver = activeGameManager.refresh(activeGame);

        if (gameOver) {
            closedGame = activeGameManager.closeGame(activeGame.getId());
        } else {
            activeGameManager.stepUp(activeGame);
        }

        final StepActiveGame stepActiveGame =
                getUpdatedActiveGameProcessor.registerStepActiveGame(
                        activeGame,
                        clientSession.getClientId(),
                        castingWarrior.getId(),
                        targetWarrior.getId(),
                        closedGame);

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/spell/exhortation", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> spellExhortation(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestParam(name = "spellExhortationId", required = true) Long spellExhortationId,
            @RequestParam(name = "position", required = true) Integer position,
            @RequestParam(name = "isMyPosition", required = true) Boolean isMyPosition) throws LogicException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior castingWarrior = activeGame.getCurrentWarrior();

        fightExceptionThrower.throwIfExpectedAnotherClient(castingWarrior, clientSession.getClientId());

        final DbSpellExhortation dbSpellExhortation = getSpellExhortation(spellExhortationId);

        final SpellExhortation spellExhortation = EntityConverter.toSpellExhortation(dbSpellExhortation, false, false);

        fightExceptionThrower.throwIfWarriorDoesNotContainSpellExhortation(castingWarrior, spellExhortation);
        fightExceptionThrower.throwIfNotEnoughMana(castingWarrior, spellExhortation.getMana());
        fightExceptionThrower.throwIfWrongTarget(activeGame, spellExhortation.getTarget(), castingWarrior, position, isMyPosition);

        activeGameManager.spellExhortation(activeGame, castingWarrior, spellExhortation, position);

        ClosedGame closedGame = null;

        final boolean gameOver = activeGameManager.refresh(activeGame);

        if (gameOver) {
            closedGame = activeGameManager.closeGame(activeGame.getId());
        } else {
            activeGameManager.stepUp(activeGame);
        }

        final StepActiveGame stepActiveGame =
                getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame, clientSession.getClientId(), closedGame);

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/spell/passive", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> spellPassive(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestParam(name = "spellPassiveId", required = true) Long spellPassiveId,
            @RequestParam(name = "warriorId", required = true) Long warriorId) throws LogicException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior castingWarrior = activeGame.getCurrentWarrior();

        fightExceptionThrower.throwIfExpectedAnotherClient(castingWarrior, clientSession.getClientId());

        final Warrior targetWarrior = activeGame.getWarriorById(warriorId);

        final DbSpellPassive dbSpellPassive = getSpellPassive(spellPassiveId);

        final SpellPassive spellPassive = EntityConverter.toSpellPassive(dbSpellPassive, false, false);

        fightExceptionThrower.throwIfWarriorDoesNotContainSpellPassive(castingWarrior, spellPassive);
        fightExceptionThrower.throwIfNotEnoughMana(castingWarrior, spellPassive.getMana());
        fightExceptionThrower.throwIfWrongTarget(activeGame, spellPassive.getTarget(), castingWarrior, targetWarrior);

        activeGameManager.spellPassive(activeGame, castingWarrior, spellPassive, targetWarrior);

        ClosedGame closedGame = null;

        final boolean gameOver = activeGameManager.refresh(activeGame);

        if (gameOver) {
            closedGame = activeGameManager.closeGame(activeGame.getId());
        } else {
            if (castingWarrior.getHero().getClient().getId() == targetWarrior.getHero().getClient().getId()) {
                activeGameManager.stepUp(activeGame, spellPassive);
            } else {
                activeGameManager.stepUp(activeGame);
            }
        }

        final StepActiveGame stepActiveGame =
                getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame, clientSession.getClientId(), closedGame);

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/next-step", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> defaultDefense(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) throws LogicException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior currentWarrior = activeGame.getCurrentWarrior();

        fightExceptionThrower.throwIfExpectedAnotherClient(currentWarrior, clientSession.getClientId());

        activeGameManager.stepUp(activeGame);

        final StepActiveGame stepActiveGame = getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame, clientSession.getClientId());

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/surrendered", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<ClientGameResult> surrendered(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) throws LogicException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        activeGame.gameOver();
        activeGame.setWinClient(activeGame.getAnotherClient(clientSession.getClientId()));

        final ClosedGame closedGame = activeGameManager.closeGame(activeGame.getId());

        final StepActiveGame stepActiveGame =
                getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame, clientSession.getClientId(), closedGame);

        return new ResponseEntity<>(stepActiveGame.getMyClientGameResult(), HttpStatus.OK);
    }

    private DbSpellAttack getSpellAttack(Long spellAttackId) throws SpellException.SpellAttackDoesNotExist {

        final DbSpellAttack dbSpellAttack = spellAttackRepository.findOne(spellAttackId);

        if (dbSpellAttack == null) {
            throw new SpellException.SpellAttackDoesNotExist(spellAttackId);
        }

        return dbSpellAttack;
    }

    private DbSpellHeal getSpellHeal(Long spellHealId) throws SpellException.SpellHeallDoesNotExist {

        final DbSpellHeal dbSpellHeal = spellHealRepository.findOne(spellHealId);

        if (dbSpellHeal == null) {
            throw new SpellException.SpellHeallDoesNotExist(spellHealId);
        }

        return dbSpellHeal;
    }

    private DbSpellExhortation getSpellExhortation(Long spellExhortationId) throws SpellException.SpellExhortationDoesNotExist {

        final DbSpellExhortation dbSpellExhortation = spellExhortationRepository.findOne(spellExhortationId);

        if (dbSpellExhortation == null) {
            throw new SpellException.SpellExhortationDoesNotExist(spellExhortationId);
        }

        return dbSpellExhortation;

    }

    private DbSpellPassive getSpellPassive(Long spellPassiveId) throws SpellException.SpellPassiveDoesNotExist {

        final DbSpellPassive dbSpellPassive = spellPassiveRepository.findOne(spellPassiveId);

        if (dbSpellPassive == null) {
            throw new SpellException.SpellPassiveDoesNotExist(spellPassiveId);
        }

        return dbSpellPassive;

    }

    private ClosedGame refreshAndStepUp(ActiveGame activeGame) throws LogicException {
        return refreshAndStepUp(activeGame, null);
    }

    private ClosedGame refreshAndStepUp(ActiveGame activeGame, SpellPassive spellPassiveForHimself) throws LogicException {

        ClosedGame closedGame = null;

        final boolean gameOver = activeGameManager.refresh(activeGame);

        if (gameOver) {
            closedGame = activeGameManager.closeGame(activeGame.getId());
        } else {
            activeGameManager.stepUp(activeGame, spellPassiveForHimself);
        }
        return closedGame;
    }

}
