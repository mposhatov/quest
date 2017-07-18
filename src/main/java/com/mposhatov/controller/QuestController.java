package com.mposhatov.controller;

import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dto.Quest;
import com.mposhatov.entity.Category;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.entity.Difficulty;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class QuestController {

    @Autowired
    private QuestRepository questRepository;

    @RequestMapping(value = "/quests",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody List<Quest> quests(@RequestBody(required = false) QuestFilter questFilter) {

        final List<Category> categories = !questFilter.getCategories().isEmpty() ?
                questFilter.getCategories() : Arrays.asList(Category.values());

        final List<Difficulty> difficulties = !questFilter.getDifficulties().isEmpty() ?
                questFilter.getDifficulties() : Arrays.asList(Difficulty.values());

        final List<DbQuest> dbQuests = questRepository.findBy(
                categories, difficulties, new PageRequest(questFilter.getPage(), 5));//todo вынести в файл

        return dbQuests.stream().map(EntityConverter::toQuest).collect(Collectors.toList());
    }
}
