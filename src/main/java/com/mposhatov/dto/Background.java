package com.mposhatov.dto;

public class Background {

    private long id;
    private String content;
    private String contentType;

    public Background(long id, String content, String contentType) {
        this.id = id;
        this.content = content;
        this.contentType = contentType;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
