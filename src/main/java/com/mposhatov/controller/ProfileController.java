package com.mposhatov.controller;

import com.mposhatov.dao.ActiveGameRepository;
import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dao.RegisteredClientRepository;
import com.mposhatov.dto.*;
import com.mposhatov.dto.Client;
import com.mposhatov.entity.*;
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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Transactional
public class ProfileController {

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ActiveGameRepository activeGameRepository;

    @RequestMapping(value = "/profile",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.GET)
    public @ResponseBody ClientWithStat getProfile(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {
        final DbRegisteredClient dbRegisteredClient = registeredClientRepository.findOne(clientSession.getClientId());

        final Client client = EntityConverter.toClient(dbRegisteredClient);

        final long quests = questRepository.count();

        final List<DbQuest> completedQuests = dbRegisteredClient.getCompletedQuests();

        final long completed = completedQuests.size();

        //todo попробовать придумать другой способ
        final long position = registeredClientRepository
                .findUpperByExperience(dbRegisteredClient.getExperience())
                .indexOf(dbRegisteredClient) + 1;

        final List<DbActiveGame> dbActiveGames = activeGameRepository.findByClient(client.getId(), false);

        final List<ActiveGame> activeGames = dbActiveGames.stream()
                .map(dbActiveGame -> {
                    ActiveGame activeGame = EntityConverter.toActiveGame(dbActiveGame);
                    if(completedQuests.contains(dbActiveGame.getQuest())) {
                        activeGame.getQuest().passed();
                    }
                    return activeGame;
                }).collect(Collectors.toList());

        final List<Long> notFreeQuests = dbRegisteredClient
                .getNotFreeQuests().stream().map(DbQuest::getId).collect(Collectors.toList());

        final ClientWithStat clientWithStat = new ClientWithStat(client, activeGames, notFreeQuests, completed, quests,
                position);

        return clientWithStat;
    }

    @RequestMapping(value = "/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<Background> addPhoto(
            @RequestPart(name = "photo", required = false) MultipartFile photo,
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {
        ResponseEntity<Background> responseEntity;
        try {
            final DbRegisteredClient dbRegisteredClient = registeredClientRepository.findOne(clientSession.getClientId());
            DbBackground dbPhoto = new DbBackground(photo.getBytes(), photo.getContentType() + ";base64");
            dbRegisteredClient.changePhoto(dbPhoto);

            responseEntity = new ResponseEntity<>(EntityConverter.toBackground(dbPhoto), HttpStatus.OK);
        } catch (IOException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}
