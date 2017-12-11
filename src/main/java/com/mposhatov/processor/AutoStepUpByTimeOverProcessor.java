package com.mposhatov.processor;

import com.mposhatov.exception.ActiveGameException;
import com.mposhatov.holder.ActiveGame;
import com.mposhatov.holder.ActiveGameHolder;
import com.mposhatov.request.GetUpdatedActiveGameProcessor;
import com.mposhatov.service.ActiveGameManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AutoStepUpByTimeOverProcessor {

    private final Logger logger = LoggerFactory.getLogger(AutoStepUpByTimeOverProcessor.class);

    @Autowired
    private ActiveGameHolder activeGameHolder;

    @Autowired
    private ActiveGameManager activeGameManager;

    @Autowired
    private GetUpdatedActiveGameProcessor getUpdatedActiveGameProcessor;

    @Value("${game.step.timeSec}")
    private int stepTimeSec;

    @Scheduled(fixedDelay = 1000)
    public void process() {

        final Date now = new Date();

        final List<ActiveGame> activeGames = activeGameHolder.getActiveGames();

        for (ActiveGame activeGame : activeGames) {
            if (TimeUnit.MILLISECONDS.toSeconds(now.getTime() - activeGame.getLastStep().getTime()) >= stepTimeSec) {
                try {
                    activeGameManager.stepUp(activeGame);
                    getUpdatedActiveGameProcessor.registerStepActiveGame(activeGame);
                } catch (ActiveGameException.InvalidCurrentStepInQueue invalidCurrentStepInQueue) {
                    logger.error(invalidCurrentStepInQueue.getMessage(), invalidCurrentStepInQueue);
                }
            }
        }
    }

}
