package com.mposhatov.controller;

import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dao.RegisteredClientRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Quest;
import com.mposhatov.entity.Category;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.entity.Difficulty;
import com.mposhatov.util.EntityConverter;
import org.apache.commons.codec.binary.Base64;
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

    @RequestMapping(value = "/quests",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody List<Quest> quests(
            @SessionAttribute(name = "com.mposhatov.dto.ClientSession", required = true) ClientSession clientSession,
            @RequestBody(required = true) QuestFilter questFilter) {

        final List<Category> categories = !questFilter.getCategories().isEmpty() ?
                questFilter.getCategories() : Arrays.asList(Category.values());

        final List<Difficulty> difficulties = !questFilter.getDifficulties().isEmpty() ?
                questFilter.getDifficulties() : Arrays.asList(Difficulty.values());

        final List<DbQuest> dbQuests = questRepository.findAvailableBy(
                categories, difficulties, new PageRequest(questFilter.getPage(), 6));//todo вынести в property

//        final List<DbQuest> dbCompletedQuests = !clientSession.isAnonymous() ?
//                registeredClientRepository.findOne(clientSession.getClientId()).getCompletedQuests() :
//                new ArrayList<>();

        final List<Quest> quests = dbQuests.stream().map(EntityConverter::toQuest).collect(Collectors.toList());

        return quests;

//        return dbQuests.stream().map(dbQuest -> {
//            final Quest quest = EntityConverter.toQuest(dbQuest);
//            if (dbCompletedQuests.contains(dbQuest)) {
//                quest.passed();
//            }
////            quest.getBackground().setContent(new String(Base64.encodeBase64(dbQuest.getBackground().getContent())));
//            return quest;
//        }).collect(Collectors.toList());
    }
}
