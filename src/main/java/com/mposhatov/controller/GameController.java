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
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGame;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.request.GetUpdatedActiveGameProcessor;
import com.mposhatov.service.ActiveGameManager;
import com.mposhatov.service.DefendSimulator;
import com.mposhatov.service.FightSimulator;
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
            @RequestParam(name = "defendingWarriorId", required = true) Long defendingWarriorId) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, ExpectedAnotherClientException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, AttackImpossibilityException, CloseActiveGameException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior attackWarrior = activeGame.getCurrentWarrior();

        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);

        throwIsExpectedAnotherClient(attackWarrior, clientSession.getClientId());
        throwIsImpossibleAttack(activeGame, attackWarrior, defendingWarrior);

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
            @RequestParam(name = "defendingWarriorId", required = true) Long defendingWarriorId) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, ExpectedAnotherClientException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, ActiveGameDoesNotContainWinClientException, SpellAttackDoesNotExistException, CloseActiveGameException, HeroDoesNotContainSpellAttackException, NotEnoughManaException, WarriorDoesNotContainSpellAttackException, AttackImpossibilityException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior attackWarrior = activeGame.getCurrentWarrior();
        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);

        throwIsExpectedAnotherClient(attackWarrior, clientSession.getClientId());
//        throwIsImpossibleAttack(activeGame, attackWarrior, defendingWarrior);

        final DbSpellAttack dbSpellAttack = getSpellAttack(spellAttackId);

        final SpellAttack spellAttack = EntityConverter.toSpellAttack(dbSpellAttack, false, false);

        throwIsWarriorDoesNotContainSpellAttack(attackWarrior, spellAttack);
        throwIsWarriorIsNotEnoughMana(attackWarrior, spellAttack.getMana());

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

    @RequestMapping(value = "/active-game.action/spell/heal", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> spellHeal(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestParam(name = "spellHealId", required = true) Long spellHealId,
            @RequestParam(name = "goalWarriorId", required = true) Long goalWarriorId) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, ExpectedAnotherClientException, SpellHeallDoesNotExistException, SpellAttackDoesNotExistException, WarriorDoesNotContainSpellAttackException, NotEnoughManaException, CloseActiveGameException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException, WarriorDoesNotContainSpellHealException, WarriorIsEnemyException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior currentWarrior = activeGame.getCurrentWarrior();
        final Warrior goalWarrior = activeGame.getWarriorById(goalWarriorId);

        throwIsExpectedAnotherClient(currentWarrior, clientSession.getClientId());
        throwIsWarriorIsEnemy(goalWarrior, clientSession.getClientId());

        final DbSpellHeal dbSpellHeal = getSpellHeal(spellHealId);

        final SpellHeal spellHeal = EntityConverter.toSpellHeal(dbSpellHeal, false, false);

        throwIsWarriorDoesNotContainSpellHeal(currentWarrior, spellHeal);
        throwIsWarriorIsNotEnoughMana(currentWarrior, spellHeal.getMana());

        currentWarrior.getWarriorCharacteristics().minusMana(spellHeal.getMana());
        goalWarrior.getWarriorCharacteristics().addHealth(spellHeal.getHealth());

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
                        currentWarrior.getId(),
                        goalWarrior.getId(),
                        closedGame);

        return new ResponseEntity<>(stepActiveGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/active-game.action/spell/exhortation", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> spellExhortation(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestParam(name = "spellExhortationId", required = true) Long spellExhortationId,
            @RequestParam(name = "position", required = true) Integer position) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ExpectedAnotherClientException, SpellExhorationDoesNotExist, WarriorDoesNotContainSpellExhortainException, NotEnoughManaException, CloseActiveGameException, ActiveGameDoesNotContainTwoClientsException, ActiveGameDoesNotContainWinClientException, GetUpdateActiveGameRequestDoesNotExistException, PositionIsBusyException, ActiveGameDoesNotContainedWarriorException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior currentWarrior = activeGame.getCurrentWarrior();

        throwIsExpectedAnotherClient(currentWarrior, clientSession.getClientId());

        final DbSpellExhortation dbSpellExhortation = getSpellExhortation(spellExhortationId);

        final SpellExhortation spellExhortation = EntityConverter.toSpellExhortation(dbSpellExhortation, false, false);

        throwIsWarriorDoesNotContainSpellExhortation(currentWarrior, spellExhortation);
        throwIsWarriorIsNotEnoughMana(currentWarrior, spellExhortation.getMana());
        throwIsPositionIsBusy(activeGame, clientSession.getClientId(), position);

        final HierarchyWarrior hierarchyWarrior = spellExhortation.getHierarchyWarrior();

        currentWarrior.getWarriorCharacteristics().minusMana(spellExhortation.getMana());

        activeGame.addWarrior(Builder.buildWarrior(activeGame, hierarchyWarrior, position, currentWarrior.getHero()));

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

    @RequestMapping(value = "/active-game.action/defense/default", method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<StepActiveGame> defaultDefense(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ExpectedAnotherClientException, ActiveGameDoesNotContainWinClientException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, CloseActiveGameException, ActiveGameDoesNotContainedWarriorException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        final Warrior currentWarrior = activeGame.getCurrentWarrior();

        throwIsExpectedAnotherClient(currentWarrior, clientSession.getClientId());

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
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) throws ClientHasNotActiveGameException, ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ExpectedAnotherClientException, ActiveGameDoesNotContainWinClientException, ActiveGameDoesNotContainTwoClientsException, GetUpdateActiveGameRequestDoesNotExistException, CloseActiveGameException, ActiveGameDoesNotContainedWarriorException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        activeGame.gameOver();
        activeGame.setWinClient(activeGame.getAnotherClient(clientSession.getClientId()));

        final ClosedGame closedGame = activeGameManager.closeGame(activeGame.getId());

        final StepActiveGame stepActiveGame =
                activeGameManager.registerStepActiveGame(activeGame, clientSession.getClientId(), closedGame);

        return new ResponseEntity<>(stepActiveGame.getMyClientGameResult(), HttpStatus.OK);
    }

    private DbSpellAttack getSpellAttack(Long spellAttackId) throws SpellAttackDoesNotExistException {

        final DbSpellAttack dbSpellAttack = spellAttackRepository.findOne(spellAttackId);

        if (dbSpellAttack == null) {
            throw new SpellAttackDoesNotExistException(spellAttackId);
        }

        return dbSpellAttack;
    }

    private DbSpellHeal getSpellHeal(Long spellHealId) throws SpellAttackDoesNotExistException, SpellHeallDoesNotExistException {

        final DbSpellHeal dbSpellHeal = spellHealRepository.findOne(spellHealId);

        if (dbSpellHeal == null) {
            throw new SpellHeallDoesNotExistException(spellHealId);
        }

        return dbSpellHeal;
    }

    private DbSpellExhortation getSpellExhortation(Long spellExhortationId) throws SpellExhorationDoesNotExist {

        final DbSpellExhortation dbSpellExhortation = spellExhortationRepository.findOne(spellExhortationId);

        if (dbSpellExhortation == null) {
            throw new SpellExhorationDoesNotExist(spellExhortationId);
        }

        return dbSpellExhortation;

    }

    private DbSpellPassive getSpellPassive(Long spellPassiveId) throws SpellPassiveDoesNotExistException {

        final DbSpellPassive dbSpellPassive = spellPassiveRepository.findOne(spellPassiveId);

        if (dbSpellPassive == null) {
            throw new SpellPassiveDoesNotExistException(spellPassiveId);
        }

        return dbSpellPassive;

    }

    private void throwIsExpectedAnotherClient(Warrior warrior, Long clientId) throws ExpectedAnotherClientException {

        if (warrior.getHero().getClient().getId() != clientId) {
            throw new ExpectedAnotherClientException(clientId);
        }

    }

    private void throwIsImpossibleAttack(ActiveGame activeGame, Warrior attackWarrior, Warrior defendingWarrior) throws AttackImpossibilityException, ClientHasNotActiveGameException {

        if (attackWarrior.getHero().getClient().getId() == defendingWarrior.getHero().getClient().getId()) {
            throw new AttackImpossibilityException(attackWarrior.getId(), defendingWarrior.getId());
        }

        if (!activeGameManager.isPossibleStrike(attackWarrior, defendingWarrior, activeGame)) {
            throw new AttackImpossibilityException(attackWarrior.getId(), defendingWarrior.getId());
        }

    }

    private void throwIsWarriorIsEnemy(Warrior warrior, Long clientId) throws ExpectedAnotherClientException, WarriorIsEnemyException {

        if (warrior.getHero().getClient().getId() != clientId) {
            throw new WarriorIsEnemyException(warrior.getId());
        }

    }

    private void throwIsWarriorDoesNotContainSpellAttack(Warrior attackWarrior, SpellAttack spellAttack) throws WarriorDoesNotContainSpellAttackException {

        if (!attackWarrior.getSpellAttacks().contains(spellAttack)) {
            throw new WarriorDoesNotContainSpellAttackException(attackWarrior.getId(), spellAttack.getId());
        }

    }

    private void throwIsWarriorIsNotEnoughMana(Warrior warrior, Integer expectedMana) throws NotEnoughManaException {

        if (warrior.getWarriorCharacteristics().getMana() < expectedMana) {
            throw new NotEnoughManaException(expectedMana, warrior.getWarriorCharacteristics().getMana());
        }

    }

    private void throwIsWarriorDoesNotContainSpellHeal(Warrior attackWarrior, SpellHeal spellHeal) throws WarriorDoesNotContainSpellHealException {

        if (!attackWarrior.getSpellHeals().contains(spellHeal)) {
            throw new WarriorDoesNotContainSpellHealException(attackWarrior.getId(), spellHeal.getId());
        }

    }

    private void throwIsWarriorDoesNotContainSpellExhortation(Warrior warrior, SpellExhortation spellExhortation) throws WarriorDoesNotContainSpellExhortainException {

        if (!warrior.getSpellExhortations().contains(spellExhortation)) {
            throw new WarriorDoesNotContainSpellExhortainException(warrior.getId(), spellExhortation.getId());
        }

    }

    private void throwIsWarriorDoesNotContainSpellPassive(Warrior warrior, SpellPassive spellPassive) throws WarriorDoesNotContainSpellPassiveException {

        if (!warrior.getSpellPassives().contains(spellPassive)) {
            throw new WarriorDoesNotContainSpellPassiveException(warrior.getId(), spellPassive.getId());
        }

    }

    private void throwIsPositionIsBusy(ActiveGame activeGame, Long clientId, Integer position) throws PositionIsBusyException, ClientHasNotActiveGameException {

        if (!activeGame.isColumnFree(clientId, position)) {
            throw new PositionIsBusyException(position);
        }

    }


}
