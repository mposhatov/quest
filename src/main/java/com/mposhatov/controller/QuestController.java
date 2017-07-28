package com.mposhatov.controller;

import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dao.RegisteredClientRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Quest;
import com.mposhatov.entity.Category;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.entity.Difficulty;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Transactional
public class QuestController {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @RequestMapping(value = "/filters", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    public @ResponseBody com.mposhatov.dto.QuestFilter questFilters() {
        final List<com.mposhatov.dto.Category> categories =
                Stream.of(Category.values()).map(EntityConverter::toCategory).collect(Collectors.toList());

        final List<com.mposhatov.dto.Difficulty> difficulties =
                Stream.of(Difficulty.values()).map(EntityConverter::toDifficulty).collect(Collectors.toList());

        return new com.mposhatov.dto.QuestFilter(categories, difficulties);
    }

    @RequestMapping(value = "/quests",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody List<Quest> quests(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestBody(required = false) QuestFilter questFilter) {

        List<DbQuest> dbCompletedQuests = new ArrayList<>();

        if(!clientSession.isAnonymous()) {
            dbCompletedQuests = registeredClientRepository
                    .findOne(clientSession.getClientId()).getCompletedQuests();
        }

        final List<Category> categories = !questFilter.getCategories().isEmpty() ?
                questFilter.getCategories() : Arrays.asList(Category.values());

        final List<Difficulty> difficulties = !questFilter.getDifficulties().isEmpty() ?
                questFilter.getDifficulties() : Arrays.asList(Difficulty.values());

        final List<DbQuest> dbQuests = questRepository.findAvailableBy(
                categories, difficulties, new PageRequest(questFilter.getPage(), 5));//todo вынести в property

        List<DbQuest> finalDbCompletedQuests = dbCompletedQuests;
        return dbQuests.stream().map(dbQuest -> {
            final Quest quest = EntityConverter.toQuest(dbQuest);
            if(finalDbCompletedQuests.contains(dbQuest)) {
                quest.passed();
            }
            return quest;
        }).collect(Collectors.toList());
    }
}
