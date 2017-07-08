package com.mposhatov.controller;

import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Step;
import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.entity.DbStep;
import com.mposhatov.service.ActiveGameService;
import com.mposhatov.util.EntityConverter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Transactional
public class ProfileController {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ActiveGameService activeGameService;

    @RequestMapping(value = "/profile", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView profile(@SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {

        final DbActiveGame dbActiveGame = activeGameService.getActiveGame(client.getId());
        ModelAndView model;

        if(dbActiveGame == null) {
            model = new ModelAndView("profile");
            model.addObject("client", client);

            final List<DbQuest> quests = questRepository.findAll(new PageRequest(0, 10)).getContent();

            model.addObject("quests", quests.stream().map(EntityConverter::toQuest).collect(Collectors.toList()));
        } else {
            model = new ModelAndView("redirect:/quest");
        }

        return model;
    }

    @RequestMapping(value = "/createGame", method = RequestMethod.POST)
    public ResponseEntity<Void> createGame(
            @RequestParam("questId") Long questId,
            @SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {
        ResponseEntity<Void> responseEntity;
        try {
            activeGameService.createGame(client.getId(), questId);
            responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/updateGame", method = RequestMethod.POST)
    public ResponseEntity<Void> updateGame(
            @RequestParam("answerId") Long answerId,
            @SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {
        ResponseEntity<Void> responseEntity;
        try {
            activeGameService.updateGame(client.getId(), answerId);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/closeGame", method = RequestMethod.POST)
    public ResponseEntity<Void> closeGame(
            @RequestParam("winning") Boolean winning,
            @SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {
        ResponseEntity<Void> responseEntity;
        try {
            activeGameService.closeGame(client.getId(), winning);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/quest", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView quest(@SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {
        ModelAndView model = new ModelAndView();
        try {
            final DbActiveGame dbActiveGame = activeGameService.getActiveGame(client.getId());
            final DbStep dbStep = dbActiveGame.getStep();

            final Step step = EntityConverter.toStep(dbStep);

            step.setAnswers(activeGameService.getAvailableAnswers(client.getId()).stream()
                    .map(EntityConverter::toAnswer).collect(Collectors.toList()));

            step.getBackground().setContent(new String(Base64.encodeBase64(dbStep.getBackground().getContent())));

            model.setViewName("step");
            model.addObject("step", step);
            model.addObject("activeGame", EntityConverter.toActiveGame(dbActiveGame));
        } catch (Exception e) {
            model.setViewName("redirect:/profile");
        }
        return model;
    }
}
