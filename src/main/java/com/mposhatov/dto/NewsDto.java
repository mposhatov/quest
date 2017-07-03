package com.mposhatov.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 23.07.2016.
 */
public class NewsDto {
    private Long id;
    private String title;
    private String resume;
    private Date date;
    private int numberOfPage;
    private List<CommentDto> comments;

    public NewsDto() {

    }

    public NewsDto(Long id, String title, String resume, Date date, int numberOfPage) {
        this.id = id;
        this.title = title;
        this.resume = resume;
        this.date = date;
        this.numberOfPage = numberOfPage;
    }

    public NewsDto(Long id, String title, String resume, Date date, int numberOfPage, List<CommentDto> comments) {
        this.id = id;
        this.title = title;
        this.resume = resume;
        this.date = date;
        this.numberOfPage = numberOfPage;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
