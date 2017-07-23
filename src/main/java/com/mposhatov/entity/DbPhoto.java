package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "PHOTO")
public class DbPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "CONTENT", nullable = false)
    private byte[] content;

    @Column(name = "CONTENT_TYPE", nullable = false)
    private String contentType;

    protected DbPhoto() {
    }

    //todo add client_id

    public DbPhoto(byte[] content, String contentType) {
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
