package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.Client;
import com.mposhatov.entity.Category;
import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.Difficulty;
import com.mposhatov.service.GameService;
import com.mposhatov.util.EntityConverter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@Transactional
public class ProfileController {

    @Autowired
    private GameService gameService;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.POST})
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/profile", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView profile(@SessionAttribute(name = "com.mposhatov.dto.Client", required = false) Client client) {

        final DbActiveGame dbActiveGame = gameService.getActiveGame(client.getId());
        final DbClient dbClient = clientRepository.findOne(client.getId());
        ModelAndView model;

        if(dbActiveGame == null) {
            model = new ModelAndView("profile");
            model.addObject("client", EntityConverter.toClient(dbClient));
            model.addObject("categories", Category.values());
            model.addObject("difficulties", Difficulty.values());
        } else {
            model = new ModelAndView("redirect:/activeGame");
        }

        return model;
    }

    @RequestMapping(value = "/activeGame", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView quest(@SessionAttribute(name = "com.mposhatov.dto.Client", required = true) Client client) {
        ModelAndView model = new ModelAndView();
        try {
            final DbActiveGame dbActiveGame = gameService.getActiveGame(client.getId());

            final ActiveGame activeGame = EntityConverter.toActiveGame(dbActiveGame);

            activeGame.getStep().setAnswers(gameService.getAvailableAnswers(dbActiveGame).stream()
                    .map(EntityConverter::toAnswer).collect(Collectors.toList()));

            activeGame.getStep().getBackground().setContent(new String(Base64.encodeBase64(dbActiveGame.getStep()
                    .getBackground().getContent())));

            model.setViewName("step");
            model.addObject("activeGame", activeGame);
        } catch (Exception e) {
            model.setViewName("redirect:/profile");
        }
        return model;
    }
}
