package com.mposhatov.controller;

import com.mposhatov.util.HomePageResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {

    @RequestMapping(value = "/entry", method = RequestMethod.GET)
    public String entry() {
        return "entry";
    }

}
