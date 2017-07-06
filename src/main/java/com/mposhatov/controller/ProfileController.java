package com.mposhatov.controller;

import com.mposhatov.dao.QuestRepository;
import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.entity.DbStep;
import com.mposhatov.service.ActiveGameService;
import com.mposhatov.springUtil.ContextHolder;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Transactional
public class ProfileController {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ContextHolder contextHolder;

    @Autowired
    private ActiveGameService activeGameService;

    @RequestMapping(value = "/profile", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView profile() {
        final ModelAndView model = new ModelAndView("profile");

        model.addObject("client", EntityConverter.toClient(contextHolder.getClient()));

        final List<DbQuest> quests = questRepository.findAll(new PageRequest(0, 10)).getContent();
        model.addObject("quests", quests.stream().map(EntityConverter::toQuest).collect(Collectors.toList()));

        return model;
    }

    @RequestMapping(value = "/createGame", method = RequestMethod.POST)
    public ResponseEntity<Void> createGame(@RequestParam("questId") Long questId) {
        ResponseEntity<Void> responseEntity;
        final DbActiveGame activeGame = activeGameService.createGame(questId);
        if (activeGame != null) {
            responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/updateGame", method = RequestMethod.POST)
    public ResponseEntity<Void> updateGame(@RequestParam("answerId") Long answerId) {
        ResponseEntity<Void> responseEntity;
        boolean update = activeGameService.updateGame(answerId);
        if (update) {
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/closeGame", method = RequestMethod.POST)
    public ResponseEntity<Void> closeGame(@RequestParam("gameCompleted") Boolean gameCompleted) {
        ResponseEntity<Void> responseEntity;
        boolean close = activeGameService.closeGame(gameCompleted);
        if (close) {
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/quest", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView quest() {
        ModelAndView model = new ModelAndView();

        final DbActiveGame dbActiveGame = activeGameService.getGame();

        if (dbActiveGame != null) {
            model.setViewName("step");
            final DbStep dbStep = dbActiveGame.getStep();
            model.addObject("step", EntityConverter.toStep(dbStep));
            model.addObject("activeGame", EntityConverter.toActiveGame(dbActiveGame));
        } else {
            model.setViewName("profile");
        }
        return model;
    }

}
