package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "BACKGROUND")
public class DbBackground {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "CONTENT", nullable = false)
    private byte[] content;

    @Column(name = "CONTENT_TYPE", nullable = false)
    private String contentType;

    protected DbBackground() {
    }

    public DbBackground(byte[] content, String contentType) {
        changeBackground(content, contentType);
    }

    public DbBackground changeBackground(byte[] content, String contentType) {
        this.content = content;
        this.contentType = contentType;
        return this;
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
