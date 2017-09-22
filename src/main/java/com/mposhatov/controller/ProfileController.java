package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dto.Background;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbClient;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@Transactional
public class ProfileController {

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<Background> addPhoto(
            @RequestPart(name = "photo", required = false) MultipartFile photo,
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {
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
