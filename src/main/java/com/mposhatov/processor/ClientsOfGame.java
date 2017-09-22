package com.mposhatov.processor;

import com.mposhatov.entity.DbClient;

public class ClientsOfGame {
    private DbClient clientFirstCommand;
    private DbClient clientSecondCommand;

    public ClientsOfGame(DbClient clientFirstCommand, DbClient clientSecondCommand) {
        this.clientFirstCommand = clientFirstCommand;
        this.clientSecondCommand = clientSecondCommand;
    }

    public DbClient getClientFirstCommand() {
        return clientFirstCommand;
    }

    public DbClient getClientSecondCommand() {
        return clientSecondCommand;
    }
}
