package com.mposhatov.dto;

import java.util.Date;
import java.util.List;

public class ClosedGame {
    private long id;
    private Date startTime;
    private Date finishTime;
    private List<ClientGameResult> clientGameResults;

    public ClosedGame(long id, Date startTime, Date finishTime, List<ClientGameResult> clientGameResults) {
        this.id = id;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.clientGameResults = clientGameResults;
    }

    public long getId() {
        return id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public List<ClientGameResult> getClientGameResults() {
        return clientGameResults;
    }
}
