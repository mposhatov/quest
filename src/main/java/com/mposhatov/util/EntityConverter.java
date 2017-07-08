package com.mposhatov.util;

import com.mposhatov.dto.*;
import com.mposhatov.entity.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EntityConverter {

    public static Client toClient(DbClient dbClient) {
        return new Client(dbClient.getId(), dbClient.getName());
    }

    public static Quest toQuest(DbQuest dbQuest) {
        return new Quest(dbQuest.getId(), dbQuest.getName(), dbQuest.getDescription());
    }

    public static Step toStep(DbStep dbStep) {
        return new Step(dbStep.getId(), dbStep.getDescription(), toBackground(dbStep.getBackground()));
    }

    public static Background toBackground(DbBackground dbBackground) {
        return new Background(dbBackground.getId(), new String(Base64.encodeBase64(dbBackground.getContent())),
                dbBackground.getContentType());
    }

    public static Answer toAnswer(DbAnswer dbAnswer) {
        return new Answer(dbAnswer.getId(), dbAnswer.getDescription(), dbAnswer.getNextStep() != null,
                dbAnswer.isWinning());
    }

    public static ActiveGame toActiveGame(DbActiveGame dbActiveGame) {
        return new ActiveGame(dbActiveGame.getId(),
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
