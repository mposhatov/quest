package com.mposhatov.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected Client() {
    }

    public Long getId() {
        return id;
    }
}
