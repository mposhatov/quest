package com.mposhatov.service;

import com.mposhatov.entity.ActiveGame;
import com.mposhatov.entity.DbClosedGame;

public abstract class GameManager {

    public abstract ActiveGame createGame(long clientId, long questId);

    public abstract ActiveGame updateGame(long activeGameId, long selectedAnswerId);

    public abstract DbClosedGame closeGame(long activeGameId, long clientId, boolean winning);

    public abstract ActiveGame getActiveGame(long clientId);

}
