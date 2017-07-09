package com.mposhatov.service;

import com.mposhatov.dao.*;
import com.mposhatov.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActiveGameService {

    private final Logger logger = LoggerFactory.getLogger(ActiveGameService.class);

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ActiveGameRepository activeGameRepository;

    @Autowired
    private ClosedGameRepository closedGameRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ClientRepository clientRepository;

    public DbActiveGame createGame(Long clientId, Long questId) throws Exception {
        try {
            final DbClient client = clientRepository.findOne(clientId);
            final DbQuest quest = questRepository.findOne(questId);
            return activeGameRepository.save(new DbActiveGame(client, quest, quest.getStartStep()));
        } catch (Exception e) {
            logger.error("Unable to create game.", e);
            throw new Exception("Unable to create game.", e);
        }
    }

    public DbActiveGame updateGame(Long activeGameId, Long selectedAnswerId) throws Exception {
        try {
            DbActiveGame activeGame = activeGameRepository.findOne(activeGameId);
            final DbAnswer answer = answerRepository.findOne(selectedAnswerId);

            activeGame.addSubjects(answer.getGivingSubjects());
            activeGame.addEvents(answer.getGivingEvents());

            final DbStep nextStep = answer.getNextStep();
            activeGame.setStep(nextStep);

            return activeGame;
        } catch (Exception e) {
            logger.error("Unable to update game.", e);
            throw new Exception("Unable to update game.", e);
        }
    }

    public DbActiveGame getActiveGame(Long clientId) {
        DbActiveGame activeGame = null;
        final DbClient client = clientRepository.findOne(clientId);
        if(client != null) {
            activeGame = activeGameRepository.findByClient(client);
        } else {
            logger.error("Unable to get active game. Client doesn't exist");
        }
        return activeGame;
    }

    public DbClosedGame closeGame(long activeGameId, long clientId, boolean winning) throws Exception {
        try {
            final Date now = new Date();

            final DbClient client = clientRepository.findOne(clientId);
            final DbActiveGame activeGame = activeGameRepository.findOne(activeGameId);
            final DbQuest quest = activeGame.getQuest();

            //todo придумать как от этого уйти
            if (winning && !client.getCompletedQuests().contains(quest)) {
                client.addCompletedQuest(quest);
            }

            activeGameRepository.delete(activeGame);

            return closedGameRepository.save(new DbClosedGame(client, quest, activeGame.getCreatedAt(), now,
                    winning));
        } catch (Exception e) {
            logger.error("Unable to close active game.", e);
            throw new Exception("Unable to close active game.", e);
        }
    }

    public List<DbAnswer> getAvailableAnswers(Long clientId) throws Exception {
        try {
            final DbActiveGame activeGame = getActiveGame(clientId);
            final List<DbSubject> receivedSubjects = activeGame.getSubjects();
            final List<DbEvent> completedEvents = activeGame.getCompletedEvents();
            final List<DbAnswer> allAnswers = activeGame.getStep().getAnswers();
            return allAnswers.stream()
                    .filter(o -> receivedSubjects.containsAll(o.getRequirementSubjects()))
                    .filter(o -> completedEvents.containsAll(o.getRequirementEvents()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Unable to get available answers.", e);
            throw new Exception("Unable to get available answers.", e);
        }
    }
}
