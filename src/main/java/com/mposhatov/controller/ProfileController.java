package com.mposhatov.controller;

import com.mposhatov.dao.QuestRepository;
import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.service.ActiveGameService;
import com.mposhatov.springUtil.ContextHolder;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @RequestMapping(value="/profile", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView profile() {
        final ModelAndView model = new ModelAndView("profile");

        model.addObject("client", EntityConverter.toClient(contextHolder.getClient()));

        final List<DbQuest> quests = questRepository.findAll(new PageRequest(0, 10)).getContent();
        model.addObject("quests", quests.stream().map(EntityConverter::toQuest).collect(Collectors.toList()));

        return model;
    }

    @RequestMapping(value="/game", method = RequestMethod.POST)
    public void game(@RequestParam("questId") Long questId) {
        activeGameService.createGame(questId);
    }

    @RequestMapping(value = "/quest", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView quest() {
        final DbActiveGame activeGame = activeGameService.getActiveGame();
        final ModelAndView model = new ModelAndView("step");
        model.addObject("step", EntityConverter.toStep(activeGame.getStep()));
        return model;
    }

}
