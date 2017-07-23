package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Photo;
import com.mposhatov.entity.DbClient;
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
    private ClientRepository clientRepository;

    @RequestMapping(value = "/profile",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.GET)
    public
    @ResponseBody
    Client profile(
            @SessionAttribute(name = "com.mposhatov.dto.Client", required = false) Client client) {
        final DbClient dbClient = clientRepository.findOne(client.getId());
        return EntityConverter.toClient(dbClient);
    }

    @RequestMapping(value = "/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Photo> addPhoto(
            @RequestParam(value = "photo", required = true) MultipartFile photo,
            @SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {
        ResponseEntity<Photo> responseEntity;
        try {
            final DbClient dbClient = clientRepository.findOne(client.getId());
            DbPhoto dbPhoto = new DbPhoto(photo.getBytes(), photo.getContentType());
            dbClient.setPhoto(dbPhoto);
            responseEntity = new ResponseEntity<>(EntityConverter.toPhoto(dbPhoto), HttpStatus.OK);
        } catch (IOException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}
