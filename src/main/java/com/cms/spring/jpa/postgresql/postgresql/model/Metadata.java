package com.cms.spring.jpa.postgresql.postgresql.model;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "metadata")
@Data
public class Metadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "plot")
    private String plot;

    @Column(name = "year")
    private int year;

    @Column(name = "language")
    private String language;

    @Column(name = "country")
    private String country;
}
