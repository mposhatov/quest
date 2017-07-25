package com.mposhatov.service;

import com.mposhatov.dao.*;
import com.mposhatov.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientActiveGameManager extends GameManager {

    private final Logger logger = LoggerFactory.getLogger(ClientActiveGameManager.class);

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ClientActiveGameRepository activeGameRepository;

    @Autowired
    private ClosedGameRepository closedGameRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private RegisteredClientRepository clientRepository;

    @Override
    public DbClientActiveGame createGame(long clientId, long questId) {
        final DbRegisteredClient client = clientRepository.findOne(clientId);
        final DbQuest quest = questRepository.findOne(questId);
        final DbClientActiveGame activeGame = new DbClientActiveGame(client, quest, quest.getStartStep());
        return activeGameRepository.save(activeGame);
    }

    @Override
    public DbClientActiveGame updateGame(long activeGameId, long selectedAnswerId) {
        final DbClientActiveGame activeGame = activeGameRepository.findOne(activeGameId);
        final DbAnswer answer = answerRepository.findOne(selectedAnswerId);

        activeGame.addSubjects(answer.getGivingSubjects());
        activeGame.addEvents(answer.getGivingEvents());

        activeGame.setStep(answer.getNextStep());

        return activeGame;
    }

    @Override
    public DbClosedGame closeGame(long activeGameId, long clientId, boolean winning) {
        DbClosedGame closedGame;

        DbRegisteredClient client = clientRepository.findOne(clientId);
        final DbClientActiveGame activeGame = activeGameRepository.findOne(activeGameId);
        final DbQuest quest = activeGame.getQuest();

        if (winning && !client.getCompletedQuests().contains(quest)) {
            client.addCompletedQuest(quest);
            client = client.addExperience(quest.getExperience());
            while (client.getExperience() >= Level.byCode(client.getLevel()).getExperienceToNextLevel()) {
                client.upLevel();
            }
        }

        closedGame = closedGameRepository.save(new DbClosedGame(client, quest, activeGame.getCreatedAt(), winning));

        activeGameRepository.delete(activeGame);

        return closedGame;
    }

    @Override
    public DbClientActiveGame getActiveGame(long clientId) {
        final DbRegisteredClient client = clientRepository.findOne(clientId);
        return activeGameRepository.findByClient(client);
    }

    public List<DbAnswer> getAvailableAnswers(DbClientActiveGame activeGame) {
        final List<DbSubject> receivedSubjects = activeGame.getSubjects();
        final List<DbEvent> completedEvents = activeGame.getCompletedEvents();
        final List<DbAnswer> allAnswers = activeGame.getStep().getAnswers();
        return allAnswers.stream()
                .filter(o -> receivedSubjects.containsAll(o.getRequirementSubjects()))
                .filter(o -> completedEvents.containsAll(o.getRequirementEvents()))
                .collect(Collectors.toList());
    }
}
