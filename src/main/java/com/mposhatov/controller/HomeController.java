package com.mposhatov.controller;

import com.mposhatov.dao.QuestRepository;
import com.mposhatov.entity.Category;
import com.mposhatov.entity.Difficulty;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class HomeController {

    @Autowired
    private QuestRepository questRepository;

    @RequestMapping(value={"/", "/home"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView goHome(@RequestParam(name = "logout", required = false) String logout,
                               @RequestParam(name = "error", required = false) String error) {
        ModelAndView model = new ModelAndView("quests");
        model.addObject("categories", Stream.of(Category.values()).map(EntityConverter::toCategory).collect(Collectors.toList()));
        model.addObject("difficulties", Stream.of(Difficulty.values()).map(EntityConverter::toDifficulty).collect(Collectors.toList()));
        if(error != null) {
            model.addObject("error", true);
        }
        return model;
    }

    @RequestMapping(value="/accessDenied", method = RequestMethod.POST)
    public String error() {
        return "accessDenied";
    }

}
