package com.mposhatov.dto;

public class Photo {

    private long id;
    private byte[] content;
    private String contentType;

    public Photo(long id, byte[] content, String contentType) {
        this.id = id;
        this.content = content;
        this.contentType = contentType;
    }

    public long getId() {
        return id;
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
