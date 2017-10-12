package com.mposhatov.processor;

import com.mposhatov.dto.Client;

public class ClientsOfGame {
    private Client firstCommand;
    private Client secondCommand;

    public ClientsOfGame(Client FirstCommand, Client secondCommand) {
        this.firstCommand = FirstCommand;
        this.secondCommand = secondCommand;
    }

    public Client getFirstCommand() {
        return firstCommand;
    }

    public Client getSecondCommand() {
        return secondCommand;
    }
}
