package com.mposhatov.controller;

import com.mposhatov.dao.ActiveGameRepository;
import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.entity.Category;
import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.Difficulty;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Transactional
public class HomeController {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ClientRepository clientRepository;

//    @Autowired
//    private CustomUserDetailsService userDetailsService;

    @Autowired
    private ActiveGameRepository activeGameRepository;

    @RequestMapping(value = {"/", "/home"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView goHome(
            HttpServletRequest request,
            @CookieValue(name = "JSESSIONID", required = true) String jsessionId,
            @RequestParam(name = "logout", required = false) String logout,
            @RequestParam(name = "error", required = false) String error) {

        ModelAndView model;

        DbClient dbClient = clientRepository.findByJsessionId(jsessionId);

        if(dbClient != null) {
            if (dbClient.isGuest()) {
                final DbActiveGame dbActiveGame = activeGameRepository.findByClient(dbClient);
                if(dbActiveGame != null) {
                    model = new ModelAndView("redirect:activeGame");
                } else {
                    model = new ModelAndView("quests");
                    model.addObject("categories", Stream.of(Category.values()).map(EntityConverter::toCategory).collect(Collectors.toList()));
                    model.addObject("difficulties", Stream.of(Difficulty.values()).map(EntityConverter::toDifficulty).collect(Collectors.toList()));
                    if (error != null) {
                        model.addObject("error", true);
                    }
                }

            } else {
//            userDetailsService.loadUserByUsername(dbClient.getName());
                model = new ModelAndView("redirect:/profile");
            }
        } else {
            dbClient = clientRepository.save(new DbClient(jsessionId));
            model = new ModelAndView("quests");
            model.addObject("categories", Stream.of(Category.values()).map(EntityConverter::toCategory).collect(Collectors.toList()));
            model.addObject("difficulties", Stream.of(Difficulty.values()).map(EntityConverter::toDifficulty).collect(Collectors.toList()));
        }

        request.getSession().setAttribute(Client.class.getName(), EntityConverter.toClient(dbClient));

        return model;
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.POST)
    public String error() {
        return "accessDenied";
    }

}
