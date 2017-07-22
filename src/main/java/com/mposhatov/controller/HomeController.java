package com.mposhatov.controller;

import com.mposhatov.dao.ActiveGameRepository;
import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.entity.DbClient;
import com.mposhatov.util.EntityConverter;
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
    private ClientRepository clientRepository;

    @Autowired
    private ActiveGameRepository activeGameRepository;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView goHome(
            HttpServletRequest request,
            @RequestParam(name = "logout", required = false) String logout,
            @RequestParam(name = "error", required = false) String error) {

        ModelAndView model;

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            model = new ModelAndView("redirect:/main");
        } else {
            model = new ModelAndView("quests");

            final String jsessionId = request.getSession(true).getId();

            DbClient dbClient = clientRepository.findByJsessionId(jsessionId);

            if (dbClient != null) {
                final DbActiveGame dbActiveGame = activeGameRepository.findByClient(dbClient);
                if (dbActiveGame != null) {
                    model = new ModelAndView("redirect:/activeGame");
                }
            } else {
                dbClient = clientRepository.save(new DbClient(jsessionId));
            }

            request.getSession().setAttribute(Client.class.getName(), EntityConverter.toClient(dbClient));

        }

        return model;
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.POST)
    public String error() {
        return "accessDenied";
    }

}
