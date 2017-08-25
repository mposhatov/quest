package com.mposhatov.controller;

import com.mposhatov.dao.ActiveGameRepository;
import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dto.*;
import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.SimpleGame;
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
    private ClientRepository clientRepository;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ActiveGameRepository activeGameRepository;

    @RequestMapping(value = "/profile",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = RequestMethod.GET)
    public @ResponseBody ClientWithStat getProfile(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession) {
        final DbClient dbClient = clientRepository.findOne(clientSession.getClientId());

        final Client client = EntityConverter.toClient(dbClient);

        final long quests = questRepository.count();

        final List<SimpleGame> completedQuests = dbClient.getCompletedQuests();

        final long completed = completedQuests.size();

        final long position = 1;
//        //todo попробовать придумать другой способ
//        final long position = clientRepository
//                .findUpperByExperience(dbClient.getCharacteristics().getExperience())
//                .indexOf(dbClient) + 1;

        final List<DbActiveGame> dbActiveGames = activeGameRepository.findByClient(client.getId());

        final List<ActiveGame> activeGames = dbActiveGames.stream()
                .map(dbActiveGame -> {
                    ActiveGame activeGame = EntityConverter.toActiveGame(dbActiveGame);
                    if(completedQuests.contains(dbActiveGame.getSimpleGame())) {
                        activeGame.getQuest().passed();
                    }
                    return activeGame;
                }).collect(Collectors.toList());

        final List<Long> notFreeQuests = dbClient
                .getBoughtQuests().stream().map(SimpleGame::getId).collect(Collectors.toList());

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
            final DbClient dbClient = clientRepository.findOne(clientSession.getClientId());
            dbClient.changePhoto(photo.getBytes(), photo.getContentType() + ";base64");//todo ;base64 что за хрень

            responseEntity = new ResponseEntity<>(EntityConverter.toBackground(dbClient.getPhoto()), HttpStatus.OK);
        } catch (IOException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}
