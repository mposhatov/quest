package com.mposhatov.util;

import com.mposhatov.dto.Answer;
import com.mposhatov.dto.Client;
import com.mposhatov.dto.Quest;
import com.mposhatov.dto.Step;
import com.mposhatov.entity.DbAnswer;
import com.mposhatov.entity.DbClient;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.entity.DbStep;
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
        return new Step(dbStep.getId(), dbStep.getDescription(), dbStep.getAnswers().stream()
                .map(EntityConverter::toAnswer).collect(Collectors.toList()));
    }

    public static Answer toAnswer(DbAnswer dbAnswer) {
        return new Answer(dbAnswer.getId(), dbAnswer.getDescription());
    }

}
