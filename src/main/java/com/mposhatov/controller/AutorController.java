package com.mposhatov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Transactional
public class AutorController {

    @RequestMapping(value="/author", method = {RequestMethod.GET, RequestMethod.POST})
    public String profile() {
        return "author";
    }

}
