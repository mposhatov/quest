package com.mposhatov.controller;

import com.mposhatov.dao.AssignRateGameRequestRepository;
import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbAssignRateGameRequest;
import com.mposhatov.entity.DbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

@Transactional
@Controller
public class ActiveGameController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AssignRateGameRequestRepository assignRateGameRequestRepository;

    //todo rename
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ResponseEntity<Void> add(@SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {
        ResponseEntity<Void> responseEntity;
        try {
            final DbClient client = clientRepository.findOne(clientSession.getClientId());
            assignRateGameRequestRepository.save(new DbAssignRateGameRequest(client));
            responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

}
