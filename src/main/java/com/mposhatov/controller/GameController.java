package com.mposhatov.controller;

import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.Client;
import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.service.GameManager;
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
    private GameManager gameManager;

    @RequestMapping(value = "/createGame", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Void> createGame(
            @RequestParam("questId") Long questId,
            @SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {
        ResponseEntity<Void> responseEntity;
        try {
            gameManager.createGame(client.getId(), questId);
            responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/updateGame", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public ResponseEntity<ActiveGame> updateGame(
            @RequestParam("selectedAnswerId") Long selectedAnswerId,
            @RequestParam("activeGameId") Long activeGameId,
            @SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {
        ResponseEntity<ActiveGame> responseEntity;
        try {
            final DbActiveGame dbActiveGame = gameManager.updateGame(activeGameId, selectedAnswerId);
            final ActiveGame activeGame = EntityConverter.toActiveGame(dbActiveGame);

            activeGame.getStep().setAnswers(gameManager.getAvailableAnswers(dbActiveGame).stream()
                    .map(EntityConverter::toAnswer).collect(Collectors.toList()));

            activeGame.getStep().getBackground().setContent(new String(Base64.encodeBase64(dbActiveGame.getStep()
                    .getBackground().getContent())));

            responseEntity = new ResponseEntity<>(activeGame, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/closeGame", method = RequestMethod.POST)
    public ResponseEntity<Void> closeGame(
            @RequestParam("activeGameId") long activeGameId,
            @SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client,
            @RequestParam("winning") boolean winning) {
        ResponseEntity<Void> responseEntity;
        try {
            gameManager.closeGame(activeGameId, client.getId(), winning);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/activeGame", method = RequestMethod.GET)
    public ModelAndView quest(@SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {
        ModelAndView model = new ModelAndView();
        try {
            final DbActiveGame dbActiveGame = gameManager.getActiveGame(client.getId());

            final ActiveGame activeGame = EntityConverter.toActiveGame(dbActiveGame);

            activeGame.getStep().setAnswers(gameManager.getAvailableAnswers(dbActiveGame).stream()
                    .map(EntityConverter::toAnswer).collect(Collectors.toList()));

            activeGame.getStep().getBackground().setContent(new String(Base64.encodeBase64(dbActiveGame.getStep()
                    .getBackground().getContent())));

            model.setViewName("step");
            model.addObject("activeGame", activeGame);
        } catch (Exception e) {
            model.setViewName("redirect:/profile");
        }
        return model;
    }

}
