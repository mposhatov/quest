package com.mposhatov.controller;

import com.mposhatov.dto.ActiveGame;
import com.mposhatov.entity.DbAnonymousActiveGame;
import com.mposhatov.entity.DbClientActiveGame;
import com.mposhatov.service.AnonymousActiveGameManager;
import com.mposhatov.service.ClientActiveGameManager;
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
    private AnonymousActiveGameManager anonymousActiveGameManager;

    @Autowired
    private ClientActiveGameManager clientActiveGameManager;

    @RequestMapping(value = "/createGame", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Void> createGame(
            @RequestParam(name = "questId", required = true) Long questId,
            @SessionAttribute(name = "com.mposhatov.controller.Client", required = true) Client currentClient) {
        ResponseEntity<Void> responseEntity;
        try {
            if(currentClient.isGuest()) {
                anonymousActiveGameManager.createGame(currentClient.getId(), questId);
            } else {
                clientActiveGameManager.createGame(currentClient.getId(), questId);
            }
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
            @SessionAttribute(name = "com.mposhatov.controller.Client", required = true) Client currentClient) {
        ResponseEntity<ActiveGame> responseEntity;
        ActiveGame activeGame;
        try {
            //todo dublicate
            if(currentClient.isGuest()) {
                final DbAnonymousActiveGame dbAnonymousActiveGame =
                        anonymousActiveGameManager.updateGame(activeGameId, selectedAnswerId);
                activeGame = EntityConverter.toActiveGame(dbAnonymousActiveGame);

                activeGame.getStep().setAnswers(anonymousActiveGameManager.getAvailableAnswers(dbAnonymousActiveGame).stream()
                        .map(EntityConverter::toAnswer).collect(Collectors.toList()));

                activeGame.getStep().getBackground().setContent(new String(Base64.encodeBase64(dbAnonymousActiveGame.getStep()
                        .getBackground().getContent())));
            } else {
                final DbClientActiveGame dbClientActiveGame =
                        clientActiveGameManager.updateGame(activeGameId, selectedAnswerId);
                activeGame = EntityConverter.toActiveGame(dbClientActiveGame);

                activeGame.getStep().setAnswers(clientActiveGameManager.getAvailableAnswers(dbClientActiveGame).stream()
                        .map(EntityConverter::toAnswer).collect(Collectors.toList()));

                activeGame.getStep().getBackground().setContent(new String(Base64.encodeBase64(dbClientActiveGame.getStep()
                        .getBackground().getContent())));
            }

            responseEntity = new ResponseEntity<>(activeGame, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/closeGame", method = RequestMethod.POST)
    public ResponseEntity<Void> closeGame(
            @RequestParam("activeGameId") long activeGameId,
            @SessionAttribute(name = "com.mposhatov.controller.Client", required = true) Client currentClient,
            @RequestParam("winning") boolean winning) {
        ResponseEntity<Void> responseEntity;
        try {
            if(currentClient.isGuest()) {
                anonymousActiveGameManager.closeGame(activeGameId, currentClient.getId(), winning);
            } else {
                clientActiveGameManager.closeGame(activeGameId, currentClient.getId(), winning);
            }
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/activeGame", method = RequestMethod.GET)
    public ModelAndView quest(@SessionAttribute(name = "com.mposhatov.controller.Client", required = true) Client currentClient) {
        ModelAndView model = new ModelAndView();
        ActiveGame activeGame;
        try {
            //todo dublicate
            if(currentClient.isGuest()) {
                final DbAnonymousActiveGame dbAnonymousActiveGame =
                        anonymousActiveGameManager.getActiveGame(currentClient.getId());

                activeGame = EntityConverter.toActiveGame(dbAnonymousActiveGame);

                activeGame.getStep().setAnswers(anonymousActiveGameManager.getAvailableAnswers(dbAnonymousActiveGame).stream()
                        .map(EntityConverter::toAnswer).collect(Collectors.toList()));

                activeGame.getStep().getBackground().setContent(new String(Base64.encodeBase64(dbAnonymousActiveGame.getStep()
                        .getBackground().getContent())));

            } else {
                final DbClientActiveGame dbClientActiveGame =
                        clientActiveGameManager.getActiveGame(currentClient.getId());

                activeGame = EntityConverter.toActiveGame(dbClientActiveGame);

                activeGame.getStep().setAnswers(clientActiveGameManager.getAvailableAnswers(dbClientActiveGame).stream()
                        .map(EntityConverter::toAnswer).collect(Collectors.toList()));

                activeGame.getStep().getBackground().setContent(new String(Base64.encodeBase64(dbClientActiveGame.getStep()
                        .getBackground().getContent())));

            }


            model.setViewName("step");
            model.addObject("activeGame", activeGame);
        } catch (Exception e) {
            model.setViewName("redirect:/profile");
        }
        return model;
    }

}
