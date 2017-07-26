package com.mposhatov.dto;

import java.util.List;

public class FullClient extends Client{

    private List<Long> completedQuests;
    private List<Long> notFreeQuests;

    public FullClient(long id, String name, Photo photo, long level, long experience, List<Long> completedQuests,
                      List<Long> notFreeQuests) {
        super(id, name, photo, level, experience);
        this.completedQuests = completedQuests;
        this.notFreeQuests = notFreeQuests;
    }

    public List<Long> getCompletedQuests() {
        return completedQuests;
    }

    public List<Long> getNotFreeQuests() {
        return notFreeQuests;
    }
}
