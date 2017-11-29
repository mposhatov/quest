package com.mposhatov.controller;

import com.mposhatov.dao.*;
import com.mposhatov.dto.Background;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbClient;
import com.mposhatov.exception.LogicException;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Transactional(noRollbackFor = LogicException.class)
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER', 'ROLE_ADMIN')")
    public ResponseEntity<List<Client>> clients(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession) {

        final List<Client> clients =
                clientRepository.findAll().stream()
                        .map(cl -> EntityConverter.toClient(cl, true, true, false, false, false))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @RequestMapping(value = "/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.POST)
    @PreAuthorize("@gameSecurity.hasAnyRolesOnClientSession(#clientSession, 'ROLE_GAMER', 'ROLE_ADVANCED_GAMER')")
    public ResponseEntity<Background> addPhoto(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = false) ClientSession clientSession,
            @RequestPart(name = "photo", required = true) MultipartFile photo) {
        ResponseEntity<Background> responseEntity;
        try {
            final DbClient dbClient = clientRepository.findOne(clientSession.getClientId());
            dbClient.changePhoto(photo.getBytes(), photo.getContentType() + ";base64");//todo ;base64 что за хрень

            responseEntity = new ResponseEntity<>(EntityConverter.toBackground(dbClient.getPhoto()), HttpStatus.OK);
        } catch (IOException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

}
