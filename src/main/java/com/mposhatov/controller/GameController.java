package com.mposhatov.controller;

import com.mposhatov.dao.ActiveGameRepository;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.service.ActiveGameManager;
import com.mposhatov.util.EntityConverter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@Transactional
public class GameController {

    @Autowired
    private ActiveGameManager activeGameManager;

    @Autowired
    private ActiveGameRepository activeGameRepository;

    @RequestMapping(value = "/createGame", method = RequestMethod.POST)
    public ResponseEntity<ActiveGame> createGame(
            @RequestParam(name = "questId", required = true) Long questId,
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {
        ResponseEntity<ActiveGame> responseEntity;
        try {
            final DbActiveGame dbActiveGame = activeGameManager.createGame(clientSession, questId);
            responseEntity = new ResponseEntity<>(EntityConverter.toActiveGame(dbActiveGame), HttpStatus.CREATED);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/updateGame", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public ResponseEntity<ActiveGame> updateGame(
            @RequestParam(name = "selectedAnswerId", required = true) Long selectedAnswerId,
            @RequestParam(name = "activeGameId", required = true) Long activeGameId,
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {
        ResponseEntity<ActiveGame> responseEntity;
        try {
            final DbActiveGame dbActiveGame = activeGameManager.updateGame(activeGameId, selectedAnswerId);
            responseEntity = new ResponseEntity<>(prepareActiveGame(dbActiveGame), HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/closeGame", method = RequestMethod.POST)
    public ResponseEntity<Void> closeGame(
            @RequestParam(name = "activeGameId", required = true) long activeGameId,
            @RequestParam(name = "winning", required = true) boolean winning,
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {
        ResponseEntity<Void> responseEntity;
        try {
            activeGameManager.closeGame(clientSession, activeGameId, winning);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/activeGame", method = RequestMethod.GET)
    public ModelAndView quest(@RequestParam(name = "activeGameId", required = true) long activeGameId) {
        final ModelAndView modelAndView = new ModelAndView("activeGame");
        final DbActiveGame dbActiveGame = activeGameRepository.findOne(activeGameId);
        modelAndView.addObject("activeGame", prepareActiveGame(dbActiveGame));
        return modelAndView;
    }

    private ActiveGame prepareActiveGame(DbActiveGame dbActiveGame) {
        ActiveGame activeGame = EntityConverter.toActiveGame(dbActiveGame);

        activeGame.getStep().setAnswers(dbActiveGame.getAvailableAnswers().stream()
                .map(EntityConverter::toAnswer).collect(Collectors.toList()));

        return activeGame;
    }

}
