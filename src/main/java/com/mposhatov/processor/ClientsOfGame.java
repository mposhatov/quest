package com.mposhatov.processor;

import com.mposhatov.dto.Client;

public class ClientsOfGame {
    private Client firstClient;
    private Client secondClient;

    public ClientsOfGame(Client firstClient, Client secondClient) {
        this.firstClient = firstClient;
        this.secondClient = secondClient;
    }

    public Client getFirstClient() {
        return firstClient;
    }

    public Client getSecondClient() {
        return secondClient;
    }
}
