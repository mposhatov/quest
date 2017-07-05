package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.QuestRepository;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.springUtil.ContextHolder;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Transactional
public class ProfileController {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ContextHolder contextHolder;

    @RequestMapping(value="/profile", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView profile() {
        final ModelAndView model = new ModelAndView("profile");

        model.addObject("client", EntityConverter.toClient(contextHolder.getClient()));

        final List<DbQuest> quests = questRepository.findAll(new PageRequest(0, 10)).getContent();
        model.addObject("quests", quests);

        return model;
    }

}
