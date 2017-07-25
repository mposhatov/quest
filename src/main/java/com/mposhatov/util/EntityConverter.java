package com.mposhatov.util;

import com.mposhatov.dto.ActiveGame;
import com.mposhatov.dto.*;
import com.mposhatov.dto.Client;
import com.mposhatov.entity.Category;
import com.mposhatov.entity.*;
import com.mposhatov.entity.Difficulty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class EntityConverter {

    public static ClientSession toClientSession(DbActiveSession dbSession) {
        return new ClientSession(dbSession.getId(), toClient(dbSession.getClient()), dbSession.getClientStatus(),
                dbSession.getCreatedAt(), dbSession.getIp(), dbSession.getUserAgent());
    }

    public static Client toClient(DbRegisteredClient dbRegisteredClient) {
        return new Client(dbRegisteredClient.getId(),
                dbRegisteredClient.getName(),
                dbRegisteredClient.getPhoto() != null ? toPhoto(dbRegisteredClient.getPhoto()) : null,
                dbRegisteredClient.getLevel(),
                dbRegisteredClient.getExperience(),
                dbRegisteredClient.getCompletedQuests() != null ?
                        dbRegisteredClient.getCompletedQuests().stream().map(DbQuest::getId).collect(Collectors.toList()) : null,
                dbRegisteredClient.getNotFreeQuests() != null ?
                        dbRegisteredClient.getNotFreeQuests().stream().map(DbQuest::getId).collect(Collectors.toList()) : null);
    }

    public static Photo toPhoto(DbPhoto dbPhoto) {
        return new Photo(dbPhoto.getId(), dbPhoto.getContent(), dbPhoto.getContentType());
    }

    public static Quest toQuest(DbQuest dbQuest) {
        return new Quest(dbQuest.getId(), dbQuest.getName(), dbQuest.getDescription(), toDifficulty(dbQuest.getDifficulty()),
                new ArrayList<>(dbQuest.getCategories().stream().map(EntityConverter::toCategory).collect(Collectors.toList())));
    }

    public static com.mposhatov.dto.Category toCategory(Category category) {
        return new com.mposhatov.dto.Category(category.name(), category.getTitle());
    }

    public static com.mposhatov.dto.Difficulty toDifficulty(Difficulty difficulty) {
        return new com.mposhatov.dto.Difficulty(difficulty.name(), difficulty.getTitle());
    }

    public static Step toStep(DbStep dbStep) {
        return new Step(dbStep.getId(), dbStep.getDescription(), toBackground(dbStep.getBackground()));
    }

    public static Background toBackground(DbBackground dbBackground) {
        return new Background(dbBackground.getId(), dbBackground.getContentType());
    }

    public static Answer toAnswer(DbAnswer dbAnswer) {
        return new Answer(dbAnswer.getId(), dbAnswer.getDescription(), dbAnswer.getNextStep() != null,
                dbAnswer.isWinning());
    }

    public static ActiveGame toActiveGame(DbAnonymousActiveGame dbActiveGame) {
        return new ActiveGame(dbActiveGame.getId(), toStep(dbActiveGame.getStep()),
                dbActiveGame.getSubjects().stream().map(EntityConverter::toSubject).collect(Collectors.toList()),
                dbActiveGame.getCompletedEvents().stream().map(EntityConverter::toEvent).collect(Collectors.toList()));
    }

    public static ActiveGame toActiveGame(DbClientActiveGame dbActiveGame) {
        return new ActiveGame(dbActiveGame.getId(), toStep(dbActiveGame.getStep()),
                dbActiveGame.getSubjects().stream().map(EntityConverter::toSubject).collect(Collectors.toList()),
                dbActiveGame.getCompletedEvents().stream().map(EntityConverter::toEvent).collect(Collectors.toList()));
    }

    public static Subject toSubject(DbSubject dbSubject) {
        return new Subject(dbSubject.getId(), dbSubject.getName(), dbSubject.getValue(), dbSubject.getNumber());
    }

    public static Event toEvent(DbEvent dbEvent) {
        return new Event(dbEvent.getId(), dbEvent.getName(), dbEvent.getValue(), dbEvent.getNumber());
    }

}
