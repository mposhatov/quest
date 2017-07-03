package com.mposhatov.dto;

/**
 * Created by user on 27.07.2016.
 */
public class CommentDto {
    private Long id;
    private String text;
    private NewsDto news;

    public CommentDto() {
    }

    public CommentDto(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public CommentDto(Long id, String text, NewsDto news) {
        this.id = id;
        this.text = text;
        this.news = news;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NewsDto getNews() {
        return news;
    }

    public void setNews(NewsDto news) {
        this.news = news;
    }
}
