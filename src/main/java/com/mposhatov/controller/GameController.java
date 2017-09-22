package com.mposhatov.controller;

import com.mposhatov.ActiveGameSessionHolder;
import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.GameSearchRequestRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.ActiveGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Transactional
@Controller
public class GameController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GameSearchRequestRepository gameSearchRequestRepository;

    @Autowired
    private ActiveGameSessionHolder activeGameSessionHolder;

    @RequestMapping(value = "/gameSession", method = RequestMethod.GET)
    public ResponseEntity<ActiveGame> getGameSession(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {
        ResponseEntity<ActiveGame> response;
        try {
            final ActiveGame activeGame = activeGameSessionHolder.getGameSessionById(clientSession.getClientId());
            response = new ResponseEntity<>(activeGame, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @RequestMapping(value = "/step", method = {RequestMethod.GET, RequestMethod.POST})
    public void step(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam("defendingWarrior") String defendingWarrior) {

    }

}
