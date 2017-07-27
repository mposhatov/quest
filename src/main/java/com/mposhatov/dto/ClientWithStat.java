package com.mposhatov.dto;

import java.util.List;

public class ClientWithStat extends FullClient{

    private long completed;
    private long quests;
    private long position;

    public ClientWithStat(FullClient client, long completed, long quests, long position) {
        super(client.getId(), client.getName(), client.getPhoto(), client.getLevel(), client.getExperience(),
                client.getCompletedQuests(), client.getNotFreeQuests());
        this.completed = completed;
        this.quests = quests;
        this.position = position;
    }

    public ClientWithStat(long id, String name, Photo photo, long level, long experience, List<Long> completedQuests,
                          List<Long> notFreeQuests, long completed, long quests, long position) {
        super(id, name, photo, level, experience, completedQuests, notFreeQuests);
        this.completed = completed;
        this.quests = quests;
        this.position = position;
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
