package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ANSWER")
public class DbAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DESCRIPTION", length = 300, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUEST_ID", nullable = false)
    private DbStep step;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "REQUIREMENT_SUBJECTS_OF_ANSWER", joinColumns = {
            @JoinColumn(name = "ANSWER_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "SUBJECT_ID", nullable = false)})
    private List<DbSubject> requirementSubjects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GIVING_SUBJECTS_OF_ANSWER", joinColumns = {
            @JoinColumn(name = "ANSWER_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "SUBJECT_ID", nullable = false)})
    private List<DbSubject> givingSubjects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "REQUIREMENT_EVENTS_OF_ANSWER", joinColumns = {
            @JoinColumn(name = "ANSWER_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "EVENT_ID", nullable = false)})
    private List<DbEvent> requirementEvents = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GIVING_EVENTS_OF_ANSWER", joinColumns = {
            @JoinColumn(name = "ANSWER_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "EVENT_ID", nullable = false)})
    private List<DbEvent> givingEvents = new ArrayList<>();

    public DbAnswer() {
    }

    public DbAnswer(String description, DbStep step) {
        this.description = description;
        this.step = step;
    }

    //todo add subject and events
}
