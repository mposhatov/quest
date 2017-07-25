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
import java.util.stream.Stream;

@Controller
public class QuestController {

    @Autowired
    private QuestRepository questRepository;

    @RequestMapping(value = "/filters", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    public @ResponseBody com.mposhatov.dto.QuestFilter questFilters() {
        final List<com.mposhatov.dto.Category> categories =
                Stream.of(Category.values()).map(EntityConverter::toCategory).collect(Collectors.toList());

        final List<com.mposhatov.dto.Difficulty> difficulties =
                Stream.of(Difficulty.values()).map(EntityConverter::toDifficulty).collect(Collectors.toList());

        return new com.mposhatov.dto.QuestFilter(categories, difficulties);
    }

    //todo не понятно. Оставить только один http доступный метод
    @RequestMapping(value = "/quests",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody List<Quest> quests(@RequestBody(required = false) QuestFilter questFilter) {

        final List<Category> categories = !questFilter.getCategories().isEmpty() ?
                questFilter.getCategories() : Arrays.asList(Category.values());

        final List<Difficulty> difficulties = !questFilter.getDifficulties().isEmpty() ?
                questFilter.getDifficulties() : Arrays.asList(Difficulty.values());

        final List<DbQuest> dbQuests = questRepository.findAvailableBy(
                categories, difficulties, new PageRequest(questFilter.getPage(), 5));//todo вынести в property

        return dbQuests.stream().map(EntityConverter::toQuest).collect(Collectors.toList());
    }
}
