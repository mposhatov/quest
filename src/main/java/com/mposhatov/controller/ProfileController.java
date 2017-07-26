package com.mposhatov.controller;

import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dao.RegisteredClientRepository;
import com.mposhatov.dto.*;
import com.mposhatov.entity.DbPhoto;
import com.mposhatov.entity.DbRegisteredClient;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@Transactional
public class ProfileController {

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Autowired
    private QuestRepository questRepository;

    @RequestMapping(value = "/profile",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.GET)
    public @ResponseBody ClientWithStat profile(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession currentClientSession) {
        final DbRegisteredClient dbRegisteredClient = registeredClientRepository.findOne(currentClientSession.getClientId());
        final FullClient fullClient = EntityConverter.toFullClient(dbRegisteredClient);

        final long quests = questRepository.count();
        final long completed = dbRegisteredClient.getCompletedQuests().size();

        final ClientWithStat clientWithStat = new ClientWithStat(fullClient, completed, quests);
        return clientWithStat;
    }

    @RequestMapping(value = "/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<Photo> addPhoto(
            @RequestPart(name = "photo", required = false) MultipartFile photo,
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession currentClientSession) {
        ResponseEntity<Photo> responseEntity;
        try {
            final DbRegisteredClient dbRegisteredClient = registeredClientRepository.findOne(currentClientSession.getClientId());
            DbPhoto dbPhoto = new DbPhoto(photo.getBytes(), photo.getContentType() + ";base64", dbRegisteredClient);
            dbRegisteredClient.changePhoto(dbPhoto);

            responseEntity = new ResponseEntity<>(EntityConverter.toPhoto(dbPhoto), HttpStatus.OK);
        } catch (IOException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}
