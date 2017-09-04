package com.mposhatov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping(value = "/entry", method = RequestMethod.GET)
    public ModelAndView entry(@RequestParam(name = "logout", required = false) String logout,
                              @RequestParam(name = "error", required = false) String error) {
        final ModelAndView modelAndView = new ModelAndView("entry");
        if(error != null && !error.isEmpty()) {
            modelAndView.addObject("error", true);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }
}
