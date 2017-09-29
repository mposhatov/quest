package com.mposhatov.entity;

import javax.persistence.*;

@Entity
@Table(name = "SPELL")
public class DbSpell {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
