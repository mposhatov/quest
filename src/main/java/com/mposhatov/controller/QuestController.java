package com.mposhatov.controller;

import com.mposhatov.dao.QuestRepository;
import com.mposhatov.dao.ClientRepository;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.dto.Quest;
import com.mposhatov.entity.Category;
import com.mposhatov.entity.SimpleGame;
import com.mposhatov.entity.Difficulty;
import com.mposhatov.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Transactional
public class QuestController {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ClientRepository clientRepository;

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

        final List<SimpleGame> simpleGames = questRepository.findAvailableBy(
                categories, difficulties, new PageRequest(questFilter.getPage(), 6));//todo вынести в property

//        final List<SimpleGame> dbCompletedQuests = !clientSession.isAnonymous() ?
//                clientRepository.findOne(clientSession.getClientId()).getCompletedQuests() :
//                new ArrayList<>();

        final List<Quest> quests = simpleGames.stream().map(EntityConverter::toQuest).collect(Collectors.toList());

        return quests;

//        return simpleGames.stream().map(dbQuest -> {
//            final Quest quest = EntityConverter.toQuest(dbQuest);
//            if (dbCompletedQuests.contains(dbQuest)) {
//                quest.passed();
//            }
////            quest.getBackgroundName().setContent(new String(Base64.encodeBase64(dbQuest.getBackgroundName().getContent())));
//            return quest;
//        }).collect(Collectors.toList());
    }
}
