package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.Category;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.Difficulty;
import com.mposhatov.entity.Role;
import com.mposhatov.util.EntityConverter;
import com.mposhatov.util.HomePageResolver;
import org.springframework.beans.factory.annotation.Autowired;
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
    private QuestRepository questRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public RedirectView goHome() {
        return new RedirectView(HomePageResolver.redirectToHomePage(), true);
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcome(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("welcome");

        final HttpSession session = request.getSession(true);

        final ClientSession clientSession = (ClientSession) session.getAttribute(ClientSession.class.getName());

        if (clientSession == null) {
            //todo параметры вынести
            final DbClient dbClient = clientRepository
                    .save(new DbClient(
                            Collections.singletonList(Role.ROLE_GAMER),
                            1, 1, 1,
                            1, 10,
                            1, 100,
                            0, 0,
                            1000, 0,
                            2, 2));

            session.setAttribute(
                    ClientSession.class.getName(),
                    new ClientSession(dbClient.getId(), Collections.singletonList(Role.ROLE_GAMER)));
        }

        //todo Авторизованный пользователь входит и NullPointer


        final List<Category> categories = Arrays.asList(Category.values());

        final List<Difficulty> difficulties = Arrays.asList(Difficulty.values());

        modelAndView.addObject("categories", categories.stream().map(EntityConverter::toCategory)
                .collect(Collectors.toList()));

        modelAndView.addObject("difficulties", difficulties.stream().map(EntityConverter::toDifficulty)
                .collect(Collectors.toList()));

//        modelAndView.addObject("quests", questRepository.findAvailableBy(categories, difficulties,
//                new PageRequest(0, 6)).stream().map(EntityConverter::toQuest)
//                .collect(Collectors.toList()));

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
