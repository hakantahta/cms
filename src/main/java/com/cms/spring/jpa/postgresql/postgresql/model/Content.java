package com.cms.spring.jpa.postgresql.postgresql.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "content")
@Data
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "metadata_id", referencedColumnName = "id", nullable = false)
    private Metadata metadata;

    private String director;

    @Column(name = "created_at")
    private Timestamp createdAt;

    // Content ve Cast arasında çoka çok ilişki
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "content_cast",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "cast_id")
    )
    private Set<Cast> casts = new HashSet<>();
}
