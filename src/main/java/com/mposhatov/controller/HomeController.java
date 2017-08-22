package com.mposhatov.controller;

import com.mposhatov.dao.AnonymousClientRepository;
import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.Category;
import com.mposhatov.entity.DbAnonymousClient;
import com.mposhatov.entity.Difficulty;
import com.mposhatov.entity.Role;
import com.mposhatov.util.EntityConverter;
import com.mposhatov.util.HomePageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Transactional
public class HomeController {

    @Autowired
    private AnonymousClientRepository anonymousClientRepository;

    @Autowired
    private QuestRepository questRepository;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public RedirectView goHome() {
        return new RedirectView(HomePageResolver.redirectToHomePage(), true);
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcome(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("welcome");

        final HttpSession session = request.getSession(true);

        //todo Авторизованный пользователь входит и NullPointer

        final DbAnonymousClient dbAnonymousClient = anonymousClientRepository.findByJsessionId(session.getId());

        if (dbAnonymousClient == null) {
            final DbAnonymousClient client = anonymousClientRepository.save(new DbAnonymousClient(session.getId()));

//            session.setMaxInactiveInterval(15);//todo properties

            session.setAttribute(ClientSession.class.getName(),
                    new ClientSession(client.getId(), Collections.singletonList(Role.ROLE_ANONYMOUS)));
        }

        final List<Category> categories = Arrays.asList(Category.values());

        final List<Difficulty> difficulties = Arrays.asList(Difficulty.values());

        modelAndView.addObject("categories", categories.stream().map(EntityConverter::toCategory)
                .collect(Collectors.toList()));

        modelAndView.addObject("difficulties", difficulties.stream().map(EntityConverter::toDifficulty)
                .collect(Collectors.toList()));

        modelAndView.addObject("quests", questRepository.findAvailableBy(categories, difficulties,
                new PageRequest(0, 6)).stream().map(EntityConverter::toQuest)
                .collect(Collectors.toList()));

        return modelAndView;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/keepAlive", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void keepAlive() {
    }

}
