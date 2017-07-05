package com.mposhatov.service;

import com.mposhatov.dao.ActiveGameRepository;
import com.mposhatov.dao.QuestRepository;
import com.mposhatov.entity.DbActiveGame;
import com.mposhatov.entity.DbQuest;
import com.mposhatov.springUtil.ContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveGameService {

    @Autowired
    private ContextHolder contextHolder;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ActiveGameRepository activeGameRepository;

    public DbActiveGame createGame(Long questId) {
        final DbQuest quest = questRepository.findOne(questId);
        final DbActiveGame activeGame = new DbActiveGame(contextHolder.getClient(), quest.getStartStep());
        activeGameRepository.save(activeGame);
        return activeGame;
    }

    public DbActiveGame getActiveGame() {
        return activeGameRepository.findByClient(contextHolder.getClient());
    }

}
