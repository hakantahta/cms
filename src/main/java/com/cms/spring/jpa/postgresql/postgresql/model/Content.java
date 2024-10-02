package com.cms.spring.jpa.postgresql.postgresql.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "content")
@Data
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "metadata_id", nullable = false)
	private Metadata metadata;

	@Column(name = "director")
	private String director;

	@Column(name = "created_at")
	private Timestamp createdAt;

	// Content ve Cast arasında çoka çok ilişki
	@ManyToMany(mappedBy = "contents") // "contents" Cast sınıfında bu ilişkiyi temsil ediyor
	private List<Cast> actors;
}
