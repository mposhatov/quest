package com.mposhatov.service;

import com.mposhatov.dao.*;
import com.mposhatov.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnonymousActiveGameManager extends GameManager {

    @Autowired
    private AnonymousClientRepository clientRepository;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private AnonymousActiveGameRepository activeGameRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ClosedGameRepository closedGameRepository;

    @Override
    public DbAnonymousActiveGame createGame(long clientId, long questId) {
        final DbAnonymousClient client = clientRepository.findOne(clientId);
        final DbQuest quest = questRepository.findOne(questId);
        final DbAnonymousActiveGame anonymousActiveGame = new DbAnonymousActiveGame(client, quest, quest.getStartStep());
        return activeGameRepository.save(anonymousActiveGame);
    }

    @Override
    public DbAnonymousActiveGame updateGame(long activeGameId, long selectedAnswerId) {
        final DbAnonymousActiveGame activeGame = activeGameRepository.findOne(activeGameId);
        final DbAnswer answer = answerRepository.findOne(selectedAnswerId);

        activeGame.addSubjects(answer.getGivingSubjects());
        activeGame.addEvents(answer.getGivingEvents());

        activeGame.setStep(answer.getNextStep());

        return activeGame;
    }

    @Override
    public DbClosedGame closeGame(long activeGameId, long clientId, boolean winning) {
        final DbAnonymousActiveGame activeGame = activeGameRepository.findOne(activeGameId);

        activeGameRepository.delete(activeGame);

        return closedGameRepository.save(new DbClosedGame(activeGame.getQuest(), activeGame.getCreatedAt(), winning));
    }

    @Override
    public DbAnonymousActiveGame getActiveGame(long clientId) {
        final DbAnonymousClient client = clientRepository.findOne(clientId);
        return activeGameRepository.findByClient(client);
    }

    public List<DbAnswer> getAvailableAnswers(DbAnonymousActiveGame activeGame) {
        final List<DbSubject> receivedSubjects = activeGame.getSubjects();
        final List<DbEvent> completedEvents = activeGame.getCompletedEvents();
        final List<DbAnswer> allAnswers = activeGame.getStep().getAnswers();
        return allAnswers.stream()
                .filter(o -> receivedSubjects.containsAll(o.getRequirementSubjects()))
                .filter(o -> completedEvents.containsAll(o.getRequirementEvents()))
                .collect(Collectors.toList());
    }
}
