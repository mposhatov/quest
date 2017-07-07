package com.mposhatov.controller;

import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Step;
import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.service.ActiveGameService;
import com.mposhatov.util.EntityConverter;
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

        final DbActiveGame game = activeGameService.getGame(client.getId());

        ModelAndView model;
        if(game != null) {
            model = new ModelAndView("redirect:/quest");
        }
        else {
            model = new ModelAndView("profile");
            model.addObject("client", client);

            final List<DbQuest> quests = questRepository.findAll(new PageRequest(0, 10)).getContent();

            model.addObject("quests", quests.stream().map(EntityConverter::toQuest).collect(Collectors.toList()));
        }

        return model;
    }

    @RequestMapping(value = "/createGame", method = RequestMethod.POST)
    public ResponseEntity<Void> createGame(
            @RequestParam("questId") Long questId,
            @SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {

        ResponseEntity<Void> responseEntity;
        final DbActiveGame activeGame = activeGameService.createGame(client.getId(), questId);
        if (activeGame != null) {
            responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/updateGame", method = RequestMethod.POST)
    public ResponseEntity<Void> updateGame(
            @RequestParam("answerId") Long answerId,
            @SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {

        ResponseEntity<Void> responseEntity;
        boolean update = activeGameService.updateGame(client.getId(), answerId);
        if (update) {
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/closeGame", method = RequestMethod.POST)
    public ResponseEntity<Void> closeGame(
            @RequestParam("gameCompleted") Boolean gameCompleted,
            @SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {

        ResponseEntity<Void> responseEntity;
        boolean close = activeGameService.closeGame(client.getId(), gameCompleted);
        if (close) {
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/quest", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView quest(@SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {
        ModelAndView model = new ModelAndView();

        final DbActiveGame game = activeGameService.getGame(client.getId());

        if (game != null) {
            model.setViewName("step");
            final Step step = EntityConverter.toStep(game.getStep());
            step.setAnswers(activeGameService.getAvailableAnswers(client.getId()).stream()
                    .map(EntityConverter::toAnswer).collect(Collectors.toList()));
            model.addObject("step", step);
            model.addObject("activeGame", EntityConverter.toActiveGame(game));
        } else {
            model.setViewName("forward:/profile");
        }
        return model;
    }

}
