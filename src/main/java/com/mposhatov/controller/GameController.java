package com.mposhatov.controller;

import com.mposhatov.GameSessionHolder;
import com.mposhatov.dao.SearchGameRequestRepository;
import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.GameSession;
import com.mposhatov.entity.DbSearchGameRequest;
import com.mposhatov.entity.DbClient;
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
    private SearchGameRequestRepository searchGameRequestRepository;

    @Autowired
    private GameSessionHolder gameSessionHolder;

    @RequestMapping(value = "/searchGame", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Void> searchGame(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {
        ResponseEntity<Void> responseEntity;
        try {
            final DbClient client = clientRepository.findOne(clientSession.getClientId());
            searchGameRequestRepository.save(new DbSearchGameRequest(client));
            responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/gameSession", method = RequestMethod.GET)
    public ResponseEntity<GameSession> getGameSession(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {
        ResponseEntity<GameSession> responseEntity;
        try {
            final GameSession gameSession = gameSessionHolder.getGameSession(clientSession.getClientId());
            responseEntity = new ResponseEntity<>(gameSession, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/step", method = {RequestMethod.GET, RequestMethod.POST})
    public void step(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam("defendingWarrior") String defendingWarrior) {

    }

}
