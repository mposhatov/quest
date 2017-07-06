package com.mposhatov.service;

import com.mposhatov.dao.ActiveGameRepository;
import com.mposhatov.dao.AnswerRepository;
import com.mposhatov.dao.ClosedGameRepository;
import com.mposhatov.dao.QuestRepository;
import com.mposhatov.entity.*;
import com.mposhatov.springUtil.ContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class ActiveGameService {

    private final Logger logger = LoggerFactory.getLogger(ActiveGameService.class);

    @Autowired
    private ContextHolder contextHolder;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ActiveGameRepository activeGameRepository;

    @Autowired
    private ClosedGameRepository closedGameRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public DbActiveGame createGame(Long questId) {
        DbActiveGame activeGame = null;

        final DbClient client = contextHolder.getClient();
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

    public boolean updateGame(Long answerId) {
        boolean update = false;
        final DbActiveGame activeGame = getGame();
        if (activeGame != null) {
            final DbAnswer answer = answerRepository.findOne(answerId);
            activeGame.addSubjects(answer.getGivingSubjects());
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

    public DbActiveGame getGame() {
        DbActiveGame game = null;
        final DbClient client = contextHolder.getClient();
        if (client != null) {
            game = activeGameRepository.findByClient(client);
        } else {
            logger.error("Unable to get active game. Client is not authorized");
        }
        return game;
    }

    public boolean closeGame(boolean gameCompleted) {
        boolean close = false;
        final Date now = new Date();
        final DbClient client = contextHolder.getClient();
        if(client != null) {
            final DbActiveGame game = getGame();
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

//    public List<DbAnswer> getAvailable() {
//        final DbActiveGame game = getGame();
//        final List<DbSubject> subjects = game.getSubjects();
//        final List<DbEvent> completedEvents = game.getCompletedEvents();
//        final List<DbAnswer> answers = game.getStep().getAnswers();
//    }

}
