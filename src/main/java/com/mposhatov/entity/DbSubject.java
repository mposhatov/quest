package com.mposhatov.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SUBJECT")
public class DbSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "MAIN", nullable = true)
    private boolean main;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBJECT_DESCRIPTION_ID", nullable = false)
    private DbSubjectDescription subjectDescription;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVENTORY_ID", nullable = false)
    private DbInventory inventory;

    protected DbSubject() {
    }

    DbSubject(DbSubjectDescription subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    DbSubject setInventory(DbInventory inventory) {
        this.inventory = inventory;
        return this;
    }

    DbSubject setMain() {
        this.main = true;
        return this;
    }


    public Long getId() {
        return id;
    }

    public boolean isMain() {
        return main;
    }

    public DbSubjectDescription getSubjectDescription() {
        return subjectDescription;
    }

    public DbInventory getInventory() {
        return inventory;
    }
}
