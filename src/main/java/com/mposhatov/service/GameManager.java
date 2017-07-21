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
public class GameManager {

    private final Logger logger = LoggerFactory.getLogger(GameManager.class);

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

    public DbActiveGame createGame(Long clientId, Long questId) {
        final DbClient client = clientRepository.findOne(clientId);
        final DbQuest quest = questRepository.findOne(questId);
        return activeGameRepository.save(new DbActiveGame(client, quest, quest.getStartStep()));
    }

    public DbActiveGame updateGame(Long activeGameId, Long selectedAnswerId) {
        DbActiveGame activeGame = activeGameRepository.findOne(activeGameId);
        final DbAnswer answer = answerRepository.findOne(selectedAnswerId);

        activeGame.addSubjects(answer.getGivingSubjects());
        activeGame.addEvents(answer.getGivingEvents());

        final DbStep nextStep = answer.getNextStep();
        activeGame.setStep(nextStep);

        return activeGame;
    }

    public DbActiveGame getActiveGame(Long clientId) {
        final DbClient client = clientRepository.findOne(clientId);
        return activeGameRepository.findByClient(client);
    }

    public DbClosedGame closeGame(long activeGameId, long clientId, boolean winning) {
        Date now = new Date();
        DbClosedGame dbClosedGame;

        DbClient client = clientRepository.findOne(clientId);
        final DbActiveGame activeGame = activeGameRepository.findOne(activeGameId);
        final DbQuest quest = activeGame.getQuest();

        if(!client.isGuest()) {
            if (winning && !client.getCompletedQuests().contains(quest)) {
                client.addCompletedQuest(quest);
                client = client.addExperience(quest.getExperience());
                while (client.getExperience() >= Level.byCode(client.getLevel()).getExperienceToNextLevel()) {
                    client.upLevel();
                }
            }
            dbClosedGame = closedGameRepository.save(
                    new DbClosedGame(client, quest, activeGame.getCreatedAt(), now, winning));
        } else {
            dbClosedGame = closedGameRepository.save(
                    new DbClosedGame(quest, activeGame.getCreatedAt(), now, winning));
        }

        activeGameRepository.delete(activeGame);

        return dbClosedGame;
    }

    public List<DbAnswer> getAvailableAnswers(DbActiveGame activeGame) {
        final List<DbSubject> receivedSubjects = activeGame.getSubjects();
        final List<DbEvent> completedEvents = activeGame.getCompletedEvents();
        final List<DbAnswer> allAnswers = activeGame.getStep().getAnswers();
        return allAnswers.stream()
                .filter(o -> receivedSubjects.containsAll(o.getRequirementSubjects()))
                .filter(o -> completedEvents.containsAll(o.getRequirementEvents()))
                .collect(Collectors.toList());
    }
}
