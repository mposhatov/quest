package com.mposhatov.dto;

import java.util.List;

public class ClientWithStat extends Client{

    private List<ActiveGame> activeGames;
    private List<Long> notFreeQuests;

    private long completed;
    private long quests;
    private long position;

    public ClientWithStat(Client client, List<ActiveGame> activeGames, List<Long> notFreeQuests, long completed,
                          long quests, long position) {
        super(client.getId(), client.getName(), client.getPhoto(), client.getLevel(), client.getExperience());
        this.activeGames = activeGames;
        this.notFreeQuests = notFreeQuests;
        this.completed = completed;
        this.quests = quests;
        this.position = position;
    }

    public List<ActiveGame> getActiveGames() {
        return activeGames;
    }

    public List<Long> getNotFreeQuests() {
        return notFreeQuests;
    }

    public long getCompleted() {
        return completed;
    }

    public long getQuests() {
        return quests;
    }

    public long getPosition() {
        return position;
    }
}
