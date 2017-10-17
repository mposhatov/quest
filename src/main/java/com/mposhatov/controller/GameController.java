package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.ClosedGameRepository;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Warrior;
import com.mposhatov.entity.Command;
import com.mposhatov.entity.DbClosedGame;
import com.mposhatov.exception.*;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.request.GetUpdateActiveGameProcessor;
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
    private ClosedGameRepository closedGameRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/active-game", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    @ResponseBody
    public DeferredResult<ActiveGame> getGameSession(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) throws ClientIsNotInTheQueueException, ActiveGameDoesNotExistException {

        final ActiveGame activeGame = activeGameHolder.getActiveGameByClientId(clientSession.getClientId());

        return getUpdateActiveGameProcessor.registerRequest(clientSession.getClientId(), activeGame.getId());
    }

    @RequestMapping(value = "/active-game.action/direct-attack", method = RequestMethod.GET)//POST
    @PreAuthorize("hasAnyRole('ROLE_GAMER', 'ROLE_GUEST')")
    public ResponseEntity<ActiveGame> directAttack(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam(name = "defendingWarriorId", required = true) long defendingWarriorId) throws ActiveGameDoesNotExistException, InvalidCurrentStepInQueueException, ActiveGameDoesNotContainedWarriorException, HitToAllyException, ClientIsNotInTheQueueException, ActiveGameDoesNotContainCommandsException, ClientHasNotActiveGameException, ExpectedAnotherWarrior {

        final long activeGameId = activeGameHolder.getActiveGameIdByClientId(clientSession.getClientId());

        ActiveGame activeGame = activeGameHolder.getActiveGameById(activeGameId);

        final Warrior attackWarrior = activeGame.getCurrentWarrior();
        final Warrior defendingWarrior = activeGame.getWarriorById(defendingWarriorId);

        if (!attackWarrior.getCommand().equals(activeGame.getCommandByClientId(clientSession.getClientId()))) {
            throw new ExpectedAnotherWarrior(attackWarrior.getId(), activeGame.getCurrentWarrior().getId());
        }

        if (attackWarrior.getCommand().equals(defendingWarrior.getCommand())) {
            throw new HitToAllyException(attackWarrior.getId(), defendingWarrior.getId());
        }

        fightSimulator.directionAttack(attackWarrior, defendingWarrior);

        if (defendingWarrior.isDead()) {
            activeGame.registerDeadWarrior(defendingWarrior);
        }

        if (!activeGame.isWin(attackWarrior.getCommand())) {
            activeGame.stepUp();
            activeGame.update();
        } else {
            activeGameHolder.deregisterActiveGame(activeGame.getId());
            DbClosedGame dbClosedGame = new DbClosedGame(activeGame.getCreateAt());
            dbClosedGame = closedGameRepository.save(dbClosedGame);

            dbClosedGame.addGameResult(
                    clientRepository.findOne(activeGame.getClientByCommand(Command.COMMAND_1).getId()),
                    activeGame.isWin(Command.COMMAND_1), activeGame.isWin(Command.COMMAND_1) ? 5 : -5)
                    .addGameResult(
                            clientRepository.findOne(activeGame.getClientByCommand(Command.COMMAND_2).getId()),
                            activeGame.isWin(Command.COMMAND_2), activeGame.isWin(Command.COMMAND_1) ? 5 : -5);

            activeGame = null;
        }

        return new ResponseEntity<>(activeGame, HttpStatus.OK);
    }
}
