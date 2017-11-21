package com.mposhatov.dto;

import java.util.Date;

public class ClosedGame {
    private Date startTime;
    private Date finishTime;
    private ClientGameResult firstClientGameResult;
    private ClientGameResult secondClientGameResult;

    public ClosedGame(Date startTime, Date finishTime, ClientGameResult firstClientGameResult, ClientGameResult secondClientGameResult) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.firstClientGameResult = firstClientGameResult;
        this.secondClientGameResult = secondClientGameResult;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public ClientGameResult getFirstClientGameResult() {
        return firstClientGameResult;
    }

    public void setFirstClientGameResult(ClientGameResult firstClientGameResult) {
        this.firstClientGameResult = firstClientGameResult;
    }

    public ClientGameResult getSecondClientGameResult() {
        return secondClientGameResult;
    }

    public void setSecondClientGameResult(ClientGameResult secondClientGameResult) {
        this.secondClientGameResult = secondClientGameResult;
    }
}
