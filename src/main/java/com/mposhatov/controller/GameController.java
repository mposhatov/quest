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
import com.mposhatov.service.DefendSimulator;
import com.mposhatov.service.FightSimulator;
import com.mposhatov.service.validator.ActiveGameExceptionThrower;
import com.mposhatov.service.validator.FightExceptionThrower;
import com.mposhatov.util.Builder;
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

    @Autowired
    private SpellHealRepository spellHealRepository;

    @Autowired
    private SpellExhortationRepository spellExhortationRepository;

    @Autowired
    private SpellPassiveRepository spellPassiveRepository;

    @Autowired
    private ActiveGameExceptionThrower activeGameExceptionThrower;

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

        fightSimulator.simpleAttack(attackWarrior, defendingWarrior);

        final ClosedGame closedGame = refreshAndStepUp(activeGame);

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

        attackWarrior.getWarriorCharacteristics().minusMana(spellAttack.getMana());

        fightSimulator.spellAttack(attackWarrior, spellAttack, defendingWarrior);

        final ClosedGame closedGame = refreshAndStepUp(activeGame);

        final StepActiveGame stepActiveGame =
                activeGameManager.registerStepActiveGame(
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

        final Warrior currentWarrior = activeGame.getCurrentWarrior();

        fightExceptionThrower.throwIfExpectedAnotherClient(currentWarrior, clientSession.getClientId());

        final Warrior targetWarrior = activeGame.getWarriorById(warriorId);

        final DbSpellHeal dbSpellHeal = getSpellHeal(spellHealId);

        final SpellHeal spellHeal = EntityConverter.toSpellHeal(dbSpellHeal, false, false);

        fightExceptionThrower.throwIfWarriorDoesNotContainSpellHeal(currentWarrior, spellHeal);
        fightExceptionThrower.throwIfNotEnoughMana(currentWarrior, spellHeal.getMana());
        fightExceptionThrower.throwIfWrongTarget(activeGame, spellHeal.getTarget(), currentWarrior, targetWarrior);

        currentWarrior.getWarriorCharacteristics().minusMana(spellHeal.getMana());
        targetWarrior.getWarriorCharacteristics().addHealth(spellHeal.getHealth());

        final ClosedGame closedGame = refreshAndStepUp(activeGame);

        final StepActiveGame stepActiveGame =
                activeGameManager.registerStepActiveGame(
                        activeGame,
                        clientSession.getClientId(),
                        currentWarrior.getId(),
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

        final Warrior currentWarrior = activeGame.getCurrentWarrior();

        fightExceptionThrower.throwIfExpectedAnotherClient(currentWarrior, clientSession.getClientId());

        final DbSpellExhortation dbSpellExhortation = getSpellExhortation(spellExhortationId);

        final SpellExhortation spellExhortation = EntityConverter.toSpellExhortation(dbSpellExhortation, false, false);

        fightExceptionThrower.throwIfWarriorDoesNotContainSpellExhortation(currentWarrior, spellExhortation);
        fightExceptionThrower.throwIfNotEnoughMana(currentWarrior, spellExhortation.getMana());
        fightExceptionThrower.throwIfWrongTarget(activeGame, spellExhortation.getTarget(), currentWarrior, position, isMyPosition);

        final HierarchyWarrior hierarchyWarrior = spellExhortation.getHierarchyWarrior();

        currentWarrior.getWarriorCharacteristics().minusMana(spellExhortation.getMana());

        activeGame.addWarrior(Builder.buildWarrior(activeGame, hierarchyWarrior, position, currentWarrior.getHero()));

        final ClosedGame closedGame = refreshAndStepUp(activeGame);

        final StepActiveGame stepActiveGame = activeGameManager.registerStepActiveGame(activeGame, clientSession.getClientId(), closedGame);

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/spell/passive", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> spellPassive(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestParam(name = "spellPassiveId", required = true) Long spellPassiveId,
            @RequestParam(name = "warriorId", required = true) Long warriorId) throws LogicException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior currentWarrior = activeGame.getCurrentWarrior();

        fightExceptionThrower.throwIfExpectedAnotherClient(currentWarrior, clientSession.getClientId());

        final Warrior targetWarrior = activeGame.getWarriorById(warriorId);

        final DbSpellPassive dbSpellPassive = getSpellPassive(spellPassiveId);

        final SpellPassive spellPassive = EntityConverter.toSpellPassive(dbSpellPassive, false, false);

        fightExceptionThrower.throwIfWarriorDoesNotContainSpellPassive(currentWarrior, spellPassive);
        fightExceptionThrower.throwIfNotEnoughMana(currentWarrior, spellPassive.getMana());
        fightExceptionThrower.throwIfWrongTarget(activeGame, spellPassive.getTarget(), currentWarrior, targetWarrior);

        currentWarrior.getWarriorCharacteristics().minusMana(spellPassive.getMana());
        targetWarrior.addEffect(Builder.buildEffect(spellPassive));

        final ClosedGame closedGame = refreshAndStepUp(activeGame);

        final StepActiveGame stepActiveGame = activeGameManager.registerStepActiveGame(activeGame, clientSession.getClientId(), closedGame);

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/defense/default", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> defaultDefense(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) throws LogicException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior currentWarrior = activeGame.getCurrentWarrior();

        fightExceptionThrower.throwIfExpectedAnotherClient(currentWarrior, clientSession.getClientId());

        defendSimulator.activateDefaultDefense(currentWarrior);

        ClosedGame closedGame = refreshAndStepUp(activeGame);

        final StepActiveGame stepActiveGame = activeGameManager.registerStepActiveGame(activeGame, clientSession.getClientId(), closedGame);

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
                activeGameManager.registerStepActiveGame(activeGame, clientSession.getClientId(), closedGame);

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

        ClosedGame closedGame = null;

        final boolean gameOver = activeGameManager.refresh(activeGame);

        if (gameOver) {
            closedGame = activeGameManager.closeGame(activeGame.getId());
        } else {
            activeGameManager.stepUp(activeGame, true);
        }
        return closedGame;
    }
}
