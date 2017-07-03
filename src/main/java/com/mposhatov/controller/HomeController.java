package com.mposhatov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping(value={"/", "/home"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView goHome(@RequestParam(name = "logout", required = false) String logout,
                               @RequestParam(name = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("loginPage");
        if(error != null) {
            modelAndView.addObject("error", true);
        }
        return modelAndView;
    }

    @RequestMapping(value="/accessDenied", method = RequestMethod.POST)
    public String error() {
        return "accessDenied";
    }

}
