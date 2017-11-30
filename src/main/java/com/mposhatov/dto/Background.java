package com.mposhatov.dto;

public class Background {

    private long clientId;
    private String content;
    private String contentType;

    public Background(long clientId, String contentType) {
        this.clientId = clientId;
        this.contentType = contentType;
    }

    public Background setContent(String content) {
        this.content = content;
        return this;
    }

    public long getClientId() {
        return clientId;
    }

    public String getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
