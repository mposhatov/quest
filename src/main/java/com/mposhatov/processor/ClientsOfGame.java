package com.mposhatov.processor;

import com.mposhatov.entity.DbClient;

public class ClientsOfGame {
    private DbClient client1;
    private DbClient client2;

    public ClientsOfGame(DbClient client1, DbClient client2) {
        this.client1 = client1;
        this.client2 = client2;
    }

    public DbClient getClient1() {
        return client1;
    }

    public DbClient getClient2() {
        return client2;
    }
}
