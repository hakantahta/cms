package com.cms.spring.jpa.postgresql.postgresql.repository;

import com.cms.spring.jpa.postgresql.postgresql.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
  List<Content> findByDirector(String director);

    @Query("SELECT c FROM Content c LEFT JOIN FETCH c.casts")
    List<Content> findAllWithCasts();
}
