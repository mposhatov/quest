package com.mposhatov.dto;

public class Background {

    private long id;
    private String content;
    private String contentType;

    public Background(long id, String contentType) {
        this.id = id;
        this.contentType = contentType;
    }

    public void setContent(String content) {
        this.content = content;
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
