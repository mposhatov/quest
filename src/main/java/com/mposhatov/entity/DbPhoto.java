package com.mposhatov.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "PHOTO")
public class DbPhoto {

    @Id
    @GeneratedValue(generator = "client")
    @GenericGenerator(name = "client", strategy = "foreign", parameters = {@org.hibernate.annotations.Parameter(name = "property", value = "client")})
    @Column(name = "CLIENT_ID")
    private Long clientId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private DbClient client;

    @Lob
    @Column(name = "CONTENT", nullable = false)
    private byte[] content;

    @Column(name = "CONTENT_TYPE", nullable = false)
    private String contentType;

    protected DbPhoto() {
    }

    public DbPhoto(DbClient client, byte[] content, String contentType) {
        this.client = client;
        changeBackground(content, contentType);
    }

    public DbPhoto changeBackground(byte[] content, String contentType) {
        this.content = content;
        this.contentType = contentType;
        return this;
    }

    public Long getClientId() {
        return clientId;
    }

    public DbClient getClient() {
        return client;
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
