package com.mposhatov.util;

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

    public static Client toClient(DbRegisteredClient dbRegClient) {
        return new Client(dbRegClient.getId(), dbRegClient.getName(),
                dbRegClient.getPhoto() != null ? toPhoto(dbRegClient.getPhoto()) : null,
                dbRegClient.getLevel(), dbRegClient.getExperience());
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

    public static ActiveGame toActiveGame(DbActiveGame dbActiveGame) {
        return new ActiveGame(dbActiveGame.getId(),
                toQuest(dbActiveGame.getQuest()),
                toStep(dbActiveGame.getStep()),
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
