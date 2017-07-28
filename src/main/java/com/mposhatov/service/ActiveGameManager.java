package com.mposhatov.service;

import com.mposhatov.dao.*;
import com.mposhatov.dto.ClientSession;
import com.mposhatov.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class ActiveGameManager {

    private final Logger logger = LoggerFactory.getLogger(ActiveGameManager.class);

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ActiveGameRepository activeGameRepository;

    @Autowired
    private ClosedGameRepository closedGameRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private RegisteredClientRepository clientRepository;

    public DbActiveGame createGame(ClientSession clientSession, long questId) {
        final DbQuest quest = questRepository.findOne(questId);
        final DbActiveGame activeGame = new DbActiveGame(clientSession.getClientId(), quest, quest.getStartStep(),
                clientSession.isAnonymous());
        return activeGameRepository.save(activeGame);
    }

    public DbActiveGame updateGame(long activeGameId, long selectedAnswerId) {
        final DbActiveGame activeGame = activeGameRepository.findOne(activeGameId);
        final DbAnswer answer = answerRepository.findOne(selectedAnswerId);

        activeGame.addSubjects(answer.getGivingSubjects());
        activeGame.addEvents(answer.getGivingEvents());

        activeGame.setStep(answer.getNextStep());

        return activeGame;
    }

    public DbClosedGame closeGame(ClientSession clientSession, long activeGameId, boolean winning) {
        DbClosedGame closedGame;

        final DbActiveGame activeGame = activeGameRepository.findOne(activeGameId);

        final DbQuest quest = activeGame.getQuest();
        final Date createdAt = activeGame.getCreatedAt();

        if(activeGame.isAnonymous()) {
            closedGame = new DbClosedGame(quest, createdAt, winning);
        } else {
            DbRegisteredClient client = clientRepository.findOne(clientSession.getClientId());

            if (winning && !client.getCompletedQuests().contains(quest)) {
                client = client.addCompletedQuest(quest).addExperience(quest.getExperience());
                while (client.getExperience() >= Level.byCode(client.getLevel()).getExperienceToNextLevel()) {
                    client.upLevel();
                }
            }

            closedGame = new DbClosedGame(client, quest, createdAt, winning);
        }

        activeGameRepository.delete(activeGame);
        closedGameRepository.save(closedGame);

        return closedGame;
    }
}