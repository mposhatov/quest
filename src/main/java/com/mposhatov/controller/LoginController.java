package com.mposhatov.controller;

import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.DbClient;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Transactional
@Controller
public class LoginController {

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/entryPage", method = RequestMethod.GET)
    public ModelAndView entry(@RequestParam(name = "logout", required = false) String logout,
                              @RequestParam(name = "error", required = false) String error) {
        final ModelAndView modelAndView = new ModelAndView("entry");
        if (error != null && !error.isEmpty()) {
            modelAndView.addObject("error", true);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/registerPage", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ResponseEntity<Client> register(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestParam(value = "login", required = true) String login,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password) {

        DbClient dbClient = clientRepository.findOne(clientSession.getClientId());

        dbClient = dbClient.login(login).password(password).email(email);

        return new ResponseEntity<>(EntityConverter.toClient(dbClient, true, true, false, false, false), HttpStatus.OK);
    }
}
