package com.mposhatov.dto;

public class ClientWithRate extends Client{

    private long position;

    public ClientWithRate(Client client, long position) {
        super(client.getId(), client.getName(), client.getPhoto(), client.getLevel(), client.getExperience());
        this.position = position;
    }

    public ClientWithRate(long id, String name, Background photo, long level, long experience, long position) {
        super(id, name, photo, level, experience);
        this.position = position;
    }

    public long getPosition() {
        return position;
    }
}
