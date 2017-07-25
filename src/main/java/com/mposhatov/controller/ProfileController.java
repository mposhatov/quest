package com.mposhatov.controller;

import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dao.RegisteredClientRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Photo;
import com.mposhatov.entity.DbRegisteredClient;
import com.mposhatov.entity.DbPhoto;
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
    public @ResponseBody Client profile(
            @SessionAttribute(name = "com.mposhatov.controller.Client", required = true) com.mposhatov.controller.Client currentClient) {
        final DbRegisteredClient dbRegisteredClient = registeredClientRepository.findOne(currentClient.getId());
        final Client client = EntityConverter.toClient(dbRegisteredClient);
        client.setQuests(questRepository.count());
        client.setCompleted(dbRegisteredClient.getCompletedQuests().size());
        return client;
    }

    @RequestMapping(value = "/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<Photo> addPhoto(
            @RequestPart(name = "photo", required = false) MultipartFile photo,
            @SessionAttribute(name = "com.mposhatov.controller.Client", required = true) com.mposhatov.controller.Client currentClient) {
        ResponseEntity<Photo> responseEntity;
        try {
            final DbRegisteredClient dbRegisteredClient = registeredClientRepository.findOne(currentClient.getId());
            DbPhoto dbPhoto = new DbPhoto(photo.getBytes(), photo.getContentType() + ";base64", dbRegisteredClient);
            dbRegisteredClient.changePhoto(dbPhoto);

            responseEntity = new ResponseEntity<>(EntityConverter.toPhoto(dbPhoto), HttpStatus.OK);
        } catch (IOException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}
