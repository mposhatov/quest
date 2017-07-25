package com.mposhatov.controller;

import com.mposhatov.dao.*;
import com.mposhatov.entity.DbAnonymousActiveGame;
import com.mposhatov.entity.DbAnonymousClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@Transactional
public class HomeController {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Autowired
    private AnonymousClientRepository anonymousClientRepository;

    @Autowired
    private AnonymousActiveGameRepository anonymousActiveGameRepository;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView goHome(
            HttpServletRequest request,
            @RequestParam(name = "logout", required = false) String logout,
            @RequestParam(name = "error", required = false) String error) {

        ModelAndView model;

        //todo нужно переписать. При ctrl + F5 не рабоатет
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            model = new ModelAndView("redirect:/main");
        } else {
            model = new ModelAndView("quests");

            final String jsessionId = request.getSession(true).getId();

            DbAnonymousClient dbAnonymousClient = anonymousClientRepository.findByJsessionId(jsessionId);

            //перекидывает на активную анонимную игру
            if (dbAnonymousClient != null) {
                final DbAnonymousActiveGame dbAnonymousActiveGame = anonymousActiveGameRepository.findByClient(dbAnonymousClient);
                if (dbAnonymousActiveGame != null) {
                    model = new ModelAndView("redirect:/activeGame");
                }
            }
        }
        return model;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.POST)
    public String error() {
        return "accessDenied";
    }

}
