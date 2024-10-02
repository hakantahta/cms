package com.cms.spring.jpa.postgresql.postgresql.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "castt")
@Data
public class Cast {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "poster")
    private String poster;

    // Cast ve Content arasında çoka çok ilişki
    @ManyToMany
    @JoinTable(
            name = "content_cast",
            joinColumns = @JoinColumn(name = "cast_id"),
            inverseJoinColumns = @JoinColumn(name = "content_id")
    )
    private List<Content> contents;
}
