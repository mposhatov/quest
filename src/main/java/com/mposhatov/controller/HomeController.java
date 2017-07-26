package com.mposhatov.controller;

import com.mposhatov.dto.ClientSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Transactional
public class HomeController {

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView goHome(
            HttpServletRequest request,
            @RequestParam(name = "logout", required = false) String logout,
            @RequestParam(name = "error", required = false) String error) {

        ModelAndView model;

        final HttpSession session = request.getSession(true);
        final ClientSession clientSession = (ClientSession) session.getAttribute(ClientSession.class.getName());

        if (!clientSession.isAnonymous()) {
            model = new ModelAndView("redirect:/main");
        } else {
            model = new ModelAndView("quests");
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
