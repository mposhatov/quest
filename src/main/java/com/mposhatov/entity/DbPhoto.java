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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbRegisteredClient client;

    protected DbPhoto() {
    }

    public DbPhoto(byte[] content, String contentType, DbRegisteredClient client) {
        this.content = content;
        this.contentType = contentType;
        this.client = client;
    }

    public DbPhoto update(byte[] content, String contentType) {
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

    public DbRegisteredClient getClient() {
        return client;
    }
}
