package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "BACKGROUND")
public class DbBackground {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CONTENT", nullable = false)
    private byte[] content;

    @Column(name = "CONTENT_TYPE", nullable = false)
    private String contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUEST_ID", nullable = true)//nullable=false
    private DbQuest quest;

    protected DbBackground() {
    }

    public DbBackground(byte[] content, String contentType, DbQuest quest) {
        this.content = content;
        this.contentType = contentType;
        this.quest = quest;
    }

    public Long getId() {
        return id;
    }

    public byte[] getContent() {
        return content;
    }

    public DbQuest getQuest() {
        return quest;
    }

    public String getContentType() {
        return contentType;
    }
}
