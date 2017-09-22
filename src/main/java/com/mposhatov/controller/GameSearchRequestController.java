package com.mposhatov.controller;

import com.mposhatov.dto.ClientSession;
import com.mposhatov.exception.LogicException;
import com.mposhatov.service.GameSearchRequestManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class GameSearchRequestController {

    private final Logger logger = LoggerFactory.getLogger(GameSearchRequestController.class);

    @Autowired
    private GameSearchRequestManager gameSearchRequestManager;

    @RequestMapping(value = "/gameSearchRequest", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_GAMER')")
    public ResponseEntity<Void> createGameSearchRequest(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {

        ResponseEntity<Void> responseEntity;

        try {
            gameSearchRequestManager.createGameSearchRequest(clientSession.getClientId());
            responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (LogicException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(HttpStatus.GONE);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/gameSearchRequest", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_GAMER')")
    public ResponseEntity<Void> deleteGameSearchRequest(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {

        ResponseEntity<Void> responseEntity;

        try {
            gameSearchRequestManager.deleteGameSearchRequest(clientSession.getClientId());
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(HttpStatus.GONE);
        }

        return responseEntity;
    }

}
