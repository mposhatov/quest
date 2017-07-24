package com.mposhatov.dto;

public class Photo {

    private Long id;
    private byte[] content;
    private String contentType;

    public Photo(Long id, byte[] content, String contentType) {
        this.id = id;
        this.content = content;
        this.contentType = contentType;
    }

    public Long getId() {
        return id;
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
