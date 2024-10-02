package com.cms.spring.jpa.postgresql.postgresql.repository;

import com.cms.spring.jpa.postgresql.postgresql.model.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetadataRepository extends JpaRepository<Metadata, Long> {
    List<Metadata> findByTitleContaining(String title);
}
