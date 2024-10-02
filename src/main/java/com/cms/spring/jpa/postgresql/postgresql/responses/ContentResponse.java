package com.cms.spring.jpa.postgresql.postgresql.responses;

import com.cms.spring.jpa.postgresql.postgresql.SimpleResponse.SimpleCastResponse;
import com.cms.spring.jpa.postgresql.postgresql.SimpleResponse.SimpleMetadataResponse;
import lombok.Data;

import java.util.List;

@Data
public class ContentResponse {

    private long id;
    private String director;
    private String createdAt;
    private SimpleMetadataResponse metadata; //ilişkiili metadata
    private List<SimpleCastResponse> actors; //ilişkili oyuncular

}
