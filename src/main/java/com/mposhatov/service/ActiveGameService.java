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

    public DbActiveGame createGame(Long clientId, Long questId) {
        DbActiveGame activeGame = null;
        final DbClient client = clientRepository.findOne(clientId);
        if (client != null) {
            final DbQuest quest = questRepository.findOne(questId);
            if (quest != null) {
                activeGame = new DbActiveGame(client, quest, quest.getStartStep());
                activeGameRepository.save(activeGame);
            } else {
                logger.error("Unable to create game. Undefined quest id = {}", questId);
            }
        } else {
            logger.error("Unable to create game. Client is not authorized");
        }
        return activeGame;
    }

    public boolean updateGame(Long clientId, Long answerId) {
        boolean update = false;
        final DbActiveGame activeGame = getGame(clientId);
        if (activeGame != null) {
            final DbAnswer answer = answerRepository.findOne(answerId);
            activeGame.addSubjects(answer.getGivingSubjects());
            activeGame.addEvents(answer.getGivingEvents());
            final DbStep nextStep = answer.getNextStep();
            if (nextStep != null) {
                activeGame.setStep(nextStep);
                update = true;
            }
        } else {
            logger.error("Unable to update game. Active game doesn't exist");
        }

        return update;
    }

    public DbActiveGame getGame(Long clientId) {
        DbActiveGame game = null;
        final DbClient client = clientRepository.findOne(clientId);
        if (client != null) {
            game = activeGameRepository.findByClient(client);
        } else {
            logger.error("Unable to get active game. Client is not authorized");
        }
        return game;
    }

    public boolean closeGame(Long clientId, boolean gameCompleted) {
        boolean close = false;
        final Date now = new Date();
        final DbClient client = clientRepository.findOne(clientId);
        if(client != null) {
            final DbActiveGame game = getGame(clientId);
            if(game != null) {
                closedGameRepository.save(new DbClosedGame(client, game.getQuest(), game.getCreatedAt(), now, gameCompleted));
                activeGameRepository.delete(game);
                close = true;
            } else {
                logger.error("Unable to close active game. Active game doesn't exist");
            }
        } else {
            logger.error("Unable to close active game. Client is not authorized");
        }
        return close;
    }

    public List<DbAnswer> getAvailableAnswers(Long clientId) {
        final DbActiveGame game = getGame(clientId);
        final List<DbSubject> receivedSubjects = game.getSubjects();
        final List<DbEvent> completedEvents = game.getCompletedEvents();
        final List<DbAnswer> answers = game.getStep().getAnswers();

        return answers.stream()
                .filter(o -> receivedSubjects.containsAll(o.getRequirementSubjects()))
                .filter(o -> completedEvents.containsAll(o.getRequirementEvents()))
                .collect(Collectors.toList());
    }

}
